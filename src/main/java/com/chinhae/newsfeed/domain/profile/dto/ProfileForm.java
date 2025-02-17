package com.chinhae.newsfeed.domain.profile.dto;

import static com.chinhae.newsfeed.global.util.StringUtil.getOrDefaultEmpty;

import com.chinhae.newsfeed.domain.profile.entity.Profile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileForm {

  private String nickname;
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
