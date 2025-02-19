package com.chinhae.newsfeed.domain.comment.dto.request;

import com.chinhae.newsfeed.global.messages.CommentConst;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @NotBlank(message = CommentConst.COMMENT_REQUIRED)
    private String content;
}
