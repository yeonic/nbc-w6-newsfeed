package com.chinhae.newsfeed.domain.profile.dto;

import com.chinhae.newsfeed.domain.post.dto.Response.PostResponseDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileView {

    private ProfileInfo profile;
    private List<PostResponseDto> posts;

    @Builder
    public ProfileView(ProfileInfo profile, List<PostResponseDto> posts) {
        this.profile = profile;
        this.posts = posts;
    }
}
