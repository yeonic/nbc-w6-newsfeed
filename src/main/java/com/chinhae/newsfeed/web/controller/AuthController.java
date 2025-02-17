package com.chinhae.newsfeed.web.controller;

import com.chinhae.newsfeed.domain.account.dto.Request.AccountLoginRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Request.AccountSignupRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Response.AccountLoginResponseDto;
import com.chinhae.newsfeed.domain.account.dto.Response.AccountSignupResponsetDto;
import com.chinhae.newsfeed.domain.account.service.AccountService;
import com.chinhae.newsfeed.global.dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/api/auth/login") // 로그인
    public Response<AccountLoginResponseDto> login(@RequestBody AccountLoginRequestDto requestDto, HttpServletRequest request){
        AccountLoginResponseDto loginUser = accountService.loginUser(requestDto);
        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_USER", loginUser);

        return Response.of(loginUser);
    }

    @PostMapping("/api/auth/logout") // 로그아웃
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
    }

    @PostMapping("/api/auth/signup") // 회원가입
    public Response<AccountSignupResponsetDto> signup(@RequestBody AccountSignupRequestDto requestDto){
        AccountSignupResponsetDto save = accountService.save(requestDto);

        return Response.of(save);
    }
}
