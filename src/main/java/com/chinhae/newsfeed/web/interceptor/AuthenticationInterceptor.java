package com.chinhae.newsfeed.web.interceptor;

import com.chinhae.newsfeed.domain.account.dto.Response.AccountResponseDto;
import com.chinhae.newsfeed.domain.account.service.AccountService;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.domain.profile.service.ProfileService;
import com.chinhae.newsfeed.global.messages.SessionKeyConst;
import com.chinhae.newsfeed.global.util.JwtUtil;
import com.chinhae.newsfeed.web.interceptor.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

        setAccountAndProfile(request, token);
        return true;
    }

    private void setAccountAndProfile(HttpServletRequest request, String token) {
        AccountResponseDto accountByEmail = accountService
            .findAccountByEmail(jwtUtil.extractEmail(token));

        log.info("account = {}", accountByEmail.getEmail());
        HttpSession session = request.getSession();
        session.setAttribute(SessionKeyConst.ACCOUNT_KEY, accountByEmail);

        ProfileInfo defaultProfile = profileService.getDefaultProfile(
            accountByEmail.getId()).orElseThrow();

        session.setAttribute(SessionKeyConst.PROFILE_KEY, defaultProfile);
    }

    private boolean isTokenValid(String token) {
        return jwtUtil.validateToken(token);
    }

    private boolean isTokenEmpty(String token) {
        return token == null || !token.startsWith("Bearer ");
    }
}
