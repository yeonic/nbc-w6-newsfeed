package com.chinhae.newsfeed.domain.account.dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class AccountUpdateResponseDto {
    private final String email;
    private final String username;
    private final String bio;
    private final String profileUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public AccountUpdateResponseDto(String email, String username, String bio, String profileUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.email = email;
        this.username = username;
        this.bio = bio;
        this.profileUrl = profileUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
