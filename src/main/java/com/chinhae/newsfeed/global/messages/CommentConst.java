package com.chinhae.newsfeed.global.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CommentConst {
    public static final String COMMENT_NOT_EXIST = "해당 댓글이 존재하지 않습니다.";
    public static final String POST_NOT_EXIST = "해당 글이 존재하지 않습니다.";
    public static final String PROFILE_NOT_MATCH = "본인이 작성한 댓글만 수정할 수 있습니다.";
    public static final String CONTENT_REQUIRED = "댓글 내용은 필수 입력값입니다.";
}
