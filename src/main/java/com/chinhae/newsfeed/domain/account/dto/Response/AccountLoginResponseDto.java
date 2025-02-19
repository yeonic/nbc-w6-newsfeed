package com.chinhae.newsfeed.domain.account.dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountLoginResponseDto {

    private final Long id;
    private final String email;
    private final String username;
    private final LocalDateTime createAt;

    public AccountLoginResponseDto(Long id, String email, String username, LocalDateTime createAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.createAt = createAt;
    }
}
