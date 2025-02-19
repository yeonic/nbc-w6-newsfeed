package com.chinhae.newsfeed.domain.profile.dto;

import static com.chinhae.newsfeed.global.util.StringUtil.getOrDefaultEmpty;

import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.global.messages.ProfileConst;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileForm {

    @Size(max = 8, message = ProfileConst.INVALID_LENGTH)
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = ProfileConst.WRONG_NICK_FORMAT)
    private String nickname;

    @Size(max = 8, message = ProfileConst.INVALID_LENGTH)
    private String bio;

    private String profileImgUrl;

    @Builder
    public ProfileForm(String nickname, String bio, String profileImgUrl) {
        this.nickname = nickname;
        this.bio = getOrDefaultEmpty(bio);
        this.profileImgUrl = getOrDefaultEmpty(profileImgUrl);
    }

    public static ProfileForm of(Profile profile) {
        return ProfileForm.builder()
            .nickname(profile.getNickname())
            .bio(profile.getBio())
            .profileImgUrl(profile.getProfileImgUrl())
            .build();
    }
}
