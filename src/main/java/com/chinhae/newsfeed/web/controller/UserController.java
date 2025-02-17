package com.chinhae.newsfeed.web.controller;

import com.chinhae.newsfeed.domain.account.dto.Response.UserResponseDto;
import com.chinhae.newsfeed.domain.account.service.AccountService;
import com.chinhae.newsfeed.global.dto.Response;
import com.chinhae.newsfeed.global.messages.LoginConst;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class UserController {

    private final AccountService accountService;

    @GetMapping("/api/users/{userId}") // 유저 조회
    public Response<UserResponseDto> findUser(@PathVariable Long userId){
        UserResponseDto allUser = accountService.findUser(userId);
        return Response.of(allUser);
    }

    @DeleteMapping("/api/users/{userId}") // 회원 탈퇴
    public void deleteUser(@PathVariable Long userId){
        accountService.deleteUser(userId);
    }

    @GetMapping("/api/users/{usersId}/setting/account")
    public


}
