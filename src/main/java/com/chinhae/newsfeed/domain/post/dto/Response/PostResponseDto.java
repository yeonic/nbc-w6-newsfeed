package com.chinhae.newsfeed.domain.post.dto.Response;

import com.chinhae.newsfeed.domain.base.dto.AuthorDto;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String content;
    private final AuthorDto postUser;
    private final Integer likeCount;
    private final Integer commentCount;
    private final Integer viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updated;

    public PostResponseDto(Long id, String content, AuthorDto postUser, Integer likeCount,
        Integer commentCount, Integer viewCount, LocalDateTime createdAt, LocalDateTime updated) {
        this.id = id;
        this.content = content;
        this.postUser = postUser;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updated = updated;
    }


}
