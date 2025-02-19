package com.chinhae.newsfeed.domain.post.dto.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private String content;


    public PostRequestDto(String content) {
        this.content = content;
    }
}
