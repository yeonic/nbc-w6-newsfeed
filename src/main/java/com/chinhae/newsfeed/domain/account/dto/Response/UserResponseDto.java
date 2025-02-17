package com.chinhae.newsfeed.domain.account.dto.Response;

import lombok.Getter;

@Getter

public class UserResponseDto {

    private final String email;
    private final String username;

    public UserResponseDto(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
