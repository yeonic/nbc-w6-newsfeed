package com.chinhae.newsfeed.global.auth;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

@Getter
public class Jwt {
    private String accessToken;

    @Builder
    public Jwt(String accessToken){
        this.accessToken = accessToken;
    }
}
