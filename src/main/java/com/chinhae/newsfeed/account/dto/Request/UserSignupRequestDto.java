package com.chinhae.newsfeed.account.dto.Request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserSignupRequestDto {

    private String email;
    private String password;
    private String username;
    private LocalDate birthDate;
}
