package com.chinhae.newsfeed.domain.account.dto.Response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountLoginResponseDto {

    private final String email;
    private final String username;
    private final LocalDateTime createAt;
    // 거기에서 받은 엔티티를 세션키에 넣어라.
    // 로그인된 계정하고 프로필도 있어야한다.
    // 로그인을 할때 프로필도 담겨야한다.

    public AccountLoginResponseDto(String email, String username, LocalDateTime createAt) {
        this.email = email;
        this.username = username;
        this.createAt = createAt;
    }
}
