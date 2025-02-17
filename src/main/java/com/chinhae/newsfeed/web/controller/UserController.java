package com.chinhae.newsfeed.web.controller;

import com.chinhae.newsfeed.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class UserController {

    private final AccountService accountService;


}
