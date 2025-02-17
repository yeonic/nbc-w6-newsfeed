package com.chinhae.newsfeed.domain.account.dto.Response;

import lombok.Getter;

import java.time.LocalDate;

@Getter

public class AccountUpdateFormResponse {
    private final String email;
    private final String username;
    private final LocalDate birthDate;

    public AccountUpdateFormResponse(String email, String username, LocalDate birthDate) {
        this.email = email;
        this.username = username;
        this.birthDate = birthDate;
    }
}
