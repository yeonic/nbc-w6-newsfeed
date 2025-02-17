package com.chinhae.newsfeed.domain.profile.service;

import com.chinhae.newsfeed.domain.profile.dto.ProfileForm;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.domain.profile.dto.ProfileView;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.domain.profile.repository.ProfileRepository;
import com.chinhae.newsfeed.global.messages.ProfileConst;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
@Service
public class ProfileService {

  private final ProfileRepository repository;

  public ProfileInfo addProfile(ProfileForm form) {

    if (!StringUtils.hasText(form.getNickname())) {
      throw new IllegalArgumentException(ProfileConst.NICK_REQUIRED);
    }

    Profile newProfile = Profile.builder()
        .nickname(form.getNickname())
        .profileImgUrl(form.getProfileImgUrl())
        .bio(form.getBio())
        .build();

    return ProfileInfo.of(repository.save(newProfile));
  }

  public ProfileView getProfile(Long profileId) {
    Profile findProfile = repository.findById(profileId).orElseThrow();
    return ProfileView.builder()
        .profile(ProfileInfo.of(findProfile))
        .build();
  }

  public ProfileForm getForm(Long profileId) {
    Profile findProfile = repository.findById(profileId).orElseThrow();
    return ProfileForm.of(findProfile);
  }

  public ProfileInfo update(Long profileId, ProfileForm updateForm) {
    Profile findProfile = repository.findById(profileId).orElseThrow();
    findProfile.updateByForm(
        updateForm.getNickname(), updateForm.getBio(), updateForm.getProfileImgUrl());

    return ProfileInfo.of(findProfile);
  }

  public void delete(Long profileId) {
    Profile findProfile = repository.findById(profileId).orElseThrow();
    repository.delete(findProfile);
  }
}
