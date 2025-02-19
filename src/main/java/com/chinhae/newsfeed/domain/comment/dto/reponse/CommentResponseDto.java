package com.chinhae.newsfeed.domain.comment.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long userId;

    private final String nickname;

    private final String profileImgUrl;

    private final String content;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public CommentResponseDto(Long userId, String nickname, String profileImgUrl, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
