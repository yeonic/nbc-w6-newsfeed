package com.chinhae.newsfeed.account.dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSigninResponseDto {

    private final String email;
    private final String username;
    private final LocalDateTime createAt;

    public UserSigninResponseDto(String email, String username, LocalDateTime createAt) {
        this.email = email;
        this.username = username;
        this.createAt = createAt;
    }
}
