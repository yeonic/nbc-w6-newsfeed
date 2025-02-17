package com.chinhae.newsfeed.domain.profile.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileView {

  private ProfileInfo profile;

  // TODO post 추가시 추가하기

  @Builder
  public ProfileView(ProfileInfo profile) {
    this.profile = profile;
  }
}
