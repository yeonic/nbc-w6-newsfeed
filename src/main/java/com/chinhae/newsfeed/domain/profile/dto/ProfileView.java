package com.chinhae.newsfeed.domain.profile.dto;

import com.chinhae.newsfeed.domain.post.dto.Response.PostPagingResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileView {

    private ProfileInfo profile;
    private PostPagingResponseDto posts;

    @Builder
    public ProfileView(ProfileInfo profile, PostPagingResponseDto posts) {
        this.profile = profile;
        this.posts = posts;
    }
}
