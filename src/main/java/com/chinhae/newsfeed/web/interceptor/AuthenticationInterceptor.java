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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("인증 헤더가 누락되었거나, 유효하지않습니다.");
            return false;
        }

        token = token.substring(7); // 앞에서 7자리를 잘라내겠다., //jwt가 bearer 타입, 불필요한 문자열을 잘라서 사용하겠다.

        if (!jwtUtil.validateToken(token)){ //
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("유효하지 않거나 만료된 토큰");
            return false;
        }

        String email = jwtUtil.extractEmail(token);
        request.setAttribute("email", email); // 컨트롤러에 사용할 수 있도록 저장

        return true;
    }
}
