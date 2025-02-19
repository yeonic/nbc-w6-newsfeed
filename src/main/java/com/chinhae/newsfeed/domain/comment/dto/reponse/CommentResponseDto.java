package com.chinhae.newsfeed.domain.comment.dto.reponse;

import com.chinhae.newsfeed.domain.base.dto.AuthorDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final AuthorDto author;

    private final String content;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public CommentResponseDto(AuthorDto author, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
