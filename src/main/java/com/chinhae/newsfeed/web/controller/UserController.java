package com.chinhae.newsfeed.web.controller;

import com.chinhae.newsfeed.domain.account.dto.Request.AccountDeleteRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Request.AccountUpdateRequestDto;
import com.chinhae.newsfeed.domain.account.dto.Response.AccountLoginResponseDto;
import com.chinhae.newsfeed.domain.account.dto.Response.AccountResponseDto;
import com.chinhae.newsfeed.domain.account.dto.Response.AccountUpdateFormResponse;
import com.chinhae.newsfeed.domain.account.dto.Response.AccountUpdateResponseDto;
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
    public Response<AccountResponseDto> findUser(@PathVariable Long userId){
        AccountResponseDto allUser = accountService.findUser(userId);

        return Response.of(allUser);
    }

    @DeleteMapping("/api/users/{userId}") // 회원 탈퇴
    public void deleteUser(@PathVariable Long userId, @RequestBody AccountDeleteRequestDto requestDto){
        accountService.deleteUser(userId, requestDto);
    }

    @GetMapping("/api/users/{userId}/setting/account") // 계정 정보 수정 폼
    public Response<AccountUpdateFormResponse> updateForm(@PathVariable Long userId){
        AccountUpdateFormResponse updateForm = accountService.updateForm(userId);

        return Response.of(updateForm);
    }

    @PatchMapping("/api/users/{userId}/setting/profile") // 계정 정보 수정
    public Response<AccountUpdateResponseDto> update(@PathVariable Long userId, @RequestBody AccountUpdateRequestDto requestDto){
        AccountUpdateResponseDto update = accountService.update(userId, requestDto);

        return Response.of(update);
    }
}
