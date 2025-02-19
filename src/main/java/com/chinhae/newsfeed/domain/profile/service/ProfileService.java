package com.chinhae.newsfeed.domain.profile.service;

import com.chinhae.newsfeed.domain.account.entity.Account;
import com.chinhae.newsfeed.domain.account.repository.AccountRepository;
import com.chinhae.newsfeed.domain.post.service.PostService;
import com.chinhae.newsfeed.domain.profile.dto.ProfileForm;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.domain.profile.dto.ProfileView;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.domain.profile.repository.ProfileRepository;
import com.chinhae.newsfeed.global.util.StringUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository repository;
    private final AccountRepository accountRepository;
    private final PostService postService;

    public ProfileInfo addProfile(Long accountId, ProfileForm form) {
        String processedNickName = form.getNickname() + "_" + StringUtil.getRandomString(12);

        Account findAccount = accountRepository.findById(accountId).orElseThrow();

        Profile newProfile = Profile.builder()
            .account(findAccount)
            .nickname(processedNickName)
            .profileImgUrl(form.getProfileImgUrl())
            .bio(form.getBio())
            .build();

        return ProfileInfo.of(repository.save(newProfile));
    }

    // 계정 생성시 생성한 프로필이 기본 프로필
    public Optional<ProfileInfo> getDefaultProfile(Long accountId) {
        List<Profile> profile = repository.findAllByAccountId(accountId);
        if (profile.isEmpty()) {
            return Optional.empty();
        }

        Profile first = profile.stream()
            .sorted(Comparator.comparingLong(Profile::getId))
            .limit(1)
            .findFirst().orElseThrow();

        return Optional.of(ProfileInfo.of(first));
    }

    public ProfileView getProfile(Long profileId) {
        Profile findProfile = repository.findById(profileId).orElseThrow();
        return ProfileView.builder()
            .profile(ProfileInfo.of(findProfile))
            .posts(postService.findAllByProfileId(profileId))
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
