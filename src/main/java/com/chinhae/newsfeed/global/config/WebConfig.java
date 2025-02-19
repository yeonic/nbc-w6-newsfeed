package com.chinhae.newsfeed.global.config;

import com.chinhae.newsfeed.web.interceptor.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/error", "/error-page/**",
                "/api/auth/login", "/api/auth/signup", "/api/profiles/[0-9]+",
                "/api/posts", "/api/posts/{postId}", "/api/posts/{postId}/comments"
            );
    }
}
