package com.chinhae.newsfeed.domain.account.dto.Request;

import lombok.Getter;

@Getter
public class UserLoginRequestDto {

    private String email;
    private String password;
}
