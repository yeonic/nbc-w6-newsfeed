package com.chinhae.newsfeed.domain.account.dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class AccountUpdateResponseDto {
    private final String email;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public AccountUpdateResponseDto(String email, String username, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.email = email;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
