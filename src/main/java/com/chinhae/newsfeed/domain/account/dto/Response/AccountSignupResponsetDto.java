package com.chinhae.newsfeed.domain.account.dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountSignupResponsetDto {
    private final String email;
    private final String name;
    private final LocalDateTime created_at;

    public AccountSignupResponsetDto(String email, String name, LocalDateTime created_at) {
        this.email = email;
        this.name = name;
        this.created_at = created_at;
    }
}
