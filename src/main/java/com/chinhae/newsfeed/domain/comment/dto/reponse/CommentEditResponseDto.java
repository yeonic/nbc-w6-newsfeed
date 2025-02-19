package com.chinhae.newsfeed.domain.comment.dto.reponse;

import lombok.Getter;

@Getter
public class CommentEditResponseDto {

    private final String content;

    public CommentEditResponseDto(String content) {
        this.content = content;
    }
}
