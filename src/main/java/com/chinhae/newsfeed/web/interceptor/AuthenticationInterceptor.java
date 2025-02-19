package com.chinhae.newsfeed.web.interceptor;

import com.chinhae.newsfeed.domain.account.dto.Response.AccountResponseDto;
import com.chinhae.newsfeed.domain.account.service.AccountService;
import com.chinhae.newsfeed.domain.profile.dto.ProfileForm;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.domain.profile.service.ProfileService;
import com.chinhae.newsfeed.global.messages.SessionKeyConst;
import com.chinhae.newsfeed.global.util.JwtUtil;
import com.chinhae.newsfeed.web.interceptor.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    public static final String NOT_VALID_HEADER = "인증 헤더가 유효하지 않습니다.";
    public static final String NOT_VALID_TOKEN = "유효하지 않은 토큰입니다.";

    private final JwtUtil jwtUtil;
    private final AccountService accountService;
    private final ProfileService profileService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        log.info("[Request] {}", request.getRequestURI());

        if (isTokenEmpty(token)) {
            throw new UnauthorizedException(NOT_VALID_HEADER);
        }

        token = token.substring(7);

        if (!isTokenValid(token)) {
            throw new UnauthorizedException(NOT_VALID_TOKEN);
        }

        return setAccountAndProfile(request, response, token);
    }

    private boolean setAccountAndProfile(HttpServletRequest request, HttpServletResponse response,
        String token) throws IOException {
        AccountResponseDto accountByEmail = accountService
            .findAccountByEmail(jwtUtil.extractEmail(token));
        request.setAttribute(SessionKeyConst.ACCOUNT_KEY, accountByEmail);

        Optional<ProfileInfo> defaultProfile = profileService.getDefaultProfile(
            accountByEmail.getId());

        if (defaultProfile.isEmpty()) {
            ProfileInfo newProfile = createDefaultProfile(accountByEmail);
            request.setAttribute(SessionKeyConst.PROFILE_KEY, newProfile);
        }

        request.setAttribute(SessionKeyConst.PROFILE_KEY, defaultProfile.orElseThrow());
        return true;
    }

    private ProfileInfo createDefaultProfile(AccountResponseDto accountByEmail) {
        String tempName = accountByEmail.getEmail().split("@")[0];
        return profileService.addProfile(accountByEmail.getId(), ProfileForm.builder()
            .nickname(tempName)
            .bio("")
            .profileImgUrl("")
            .build());
    }

    private boolean isTokenValid(String token) {
        return jwtUtil.validateToken(token);
    }

    private boolean isTokenEmpty(String token) {
        return token == null || !token.startsWith("Bearer ");
    }
}
