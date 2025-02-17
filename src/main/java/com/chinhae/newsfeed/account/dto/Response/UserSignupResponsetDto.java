package com.chinhae.newsfeed.account.dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSignupResponsetDto {
    private final String email;
    private final String name;
    private final LocalDateTime created_at;

    public UserSignupResponsetDto(String email, String name, LocalDateTime created_at) {
        this.email = email;
        this.name = name;
        this.created_at = created_at;
    }
}
