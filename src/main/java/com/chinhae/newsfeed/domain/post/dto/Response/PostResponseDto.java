package com.chinhae.newsfeed.domain.post.dto.Response;

import com.chinhae.newsfeed.domain.base.dto.AuthorDto;
import com.chinhae.newsfeed.domain.post.entity.Post;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {

    private final Long id;
    private final String content;
    private final AuthorDto postUser;
    private final Integer likeCount;
    private final Integer commentCount;
    private final Integer viewCount;
    private boolean isLiked;
    private final LocalDateTime createdAt;
    private final LocalDateTime updated;

    public PostResponseDto(Long id, String content, AuthorDto postUser, Integer likeCount,
        Integer commentCount, Integer viewCount, LocalDateTime createdAt,
        LocalDateTime updated) {
        this.id = id;
        this.content = content;
        this.postUser = postUser;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updated = updated;
    }

    public static PostResponseDto of(Post post) {
        Profile profile = post.getProfile();
        AuthorDto authorDto = new AuthorDto(profile.getId(), profile.getNickname(),
            profile.getProfileImgUrl());
        return new PostResponseDto(
            post.getId(), post.getContent(), authorDto, post.getLikeCount(),
            post.getCommentCount(), post.getViewCount(), post.getCreated_at(),
            post.getUpdated_at());
    }

    public static PostResponseDto of(Post post, boolean isLiked) {
        Profile profile = post.getProfile();
        AuthorDto authorDto = new AuthorDto(profile.getId(), profile.getNickname(),
            profile.getProfileImgUrl());
        return new PostResponseDto(
            post.getId(), post.getContent(), authorDto, post.getLikeCount(),
            post.getCommentCount(), post.getViewCount(), isLiked, post.getCreated_at(),
            post.getUpdated_at());
    }
}
