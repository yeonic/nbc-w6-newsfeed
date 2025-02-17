package com.chinhae.newsfeed.domain.profile.dto;

import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileInfo {

  private String nickname;
  private String profileImgUrl;
  private String bio;
  private int friendsCount;
  private int postsCount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  public ProfileInfo(String nickname, String profileImgUrl, String bio, int friendsCount,
      int postsCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.nickname = nickname;
    this.profileImgUrl = profileImgUrl;
    this.bio = bio;
    this.friendsCount = friendsCount;
    this.postsCount = postsCount;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static ProfileInfo of(Profile profile) {
    return ProfileInfo.builder()
        .nickname(profile.getNickname())
        .profileImgUrl(profile.getProfileImgUrl())
        .bio(profile.getBio())
        .friendsCount(profile.getFriendsCount())
        .postsCount(profile.getPostsCount())
        .createdAt(profile.getCreated_at())
        .updatedAt(profile.getUpdated_at())
        .build();
  }
}
