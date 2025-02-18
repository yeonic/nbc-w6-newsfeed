package com.chinhae.newsfeed.global.config;

import com.chinhae.newsfeed.domain.account.repository.AccountRepository;
import com.chinhae.newsfeed.global.security.JwtUtil;
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
        String token = request.getHeader("Authoriztion");

        if (token == null || !token.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("인증 헤더가 누락되었거나, 유효하지않습니다.");
            return false;
        }

        token = token.substring(7);

        if (!jwtUtil.validateToken(token)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("유효하지 않거나 만료된 토큰");
            return false;
        }

        String email = jwtUtil.extractEmail(token);
        request.setAttribute("email", email); // 컨트롤러에 사용할 수 있도록 저장

        return true;
    }
}
