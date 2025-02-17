package com.chinhae.newsfeed.account.controller;

import com.chinhae.newsfeed.account.dto.Request.UserSignupRequestDto;
import com.chinhae.newsfeed.account.dto.Response.UserSignupResponsetDto;
import com.chinhae.newsfeed.account.service.AccountService;
import com.chinhae.newsfeed.global.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class UserController {

    private final AccountService accountService;


}
