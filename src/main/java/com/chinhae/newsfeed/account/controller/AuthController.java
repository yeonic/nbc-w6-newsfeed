package com.chinhae.newsfeed.account.controller;

import com.chinhae.newsfeed.account.dto.Request.UserLoginRequestDto;
import com.chinhae.newsfeed.account.dto.Request.UserSignupRequestDto;
import com.chinhae.newsfeed.account.dto.Response.UserLoginResponseDto;
import com.chinhae.newsfeed.account.dto.Response.UserSignupResponsetDto;
import com.chinhae.newsfeed.account.service.AccountService;
import com.chinhae.newsfeed.global.dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class AuthController {

    private final AccountService accountService;

    @PostMapping("api/auth/login") // 로그인
    public Response<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto requestDto, HttpServletRequest request){
        UserLoginResponseDto loginUser = accountService.loginUser(requestDto);

        HttpSession session = request.getSession();

        session.setAttribute("LOGIN_USER", loginUser);

        return Response.of(loginUser);
    }

    @PostMapping("api/auth/logout") // 로그아웃
    public Response<String> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return Response.of("로그아웃을 성공했습니다.");
    }

    @PostMapping("api/auth/signup") // 회원가입
    public Response<UserSignupResponsetDto> signup(@RequestBody UserSignupRequestDto requestDto){
        UserSignupResponsetDto save = accountService.save(requestDto);

        return Response.of(save);
    }
}
