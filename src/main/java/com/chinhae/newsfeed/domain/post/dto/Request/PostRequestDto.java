package com.chinhae.newsfeed.domain.post.dto.Request;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String content;


    public PostRequestDto(String content) {
        this.content = content;
    }
}
