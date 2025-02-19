package com.chinhae.newsfeed.domain.account.dto.Response;

import lombok.Getter;

@Getter
public class AccountResponseDto {

    private final Long id;
    private final String email;
    private final String username;

    public AccountResponseDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
