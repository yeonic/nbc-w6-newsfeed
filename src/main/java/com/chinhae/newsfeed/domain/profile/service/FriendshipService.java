package com.chinhae.newsfeed.domain.profile.service;

import com.chinhae.newsfeed.domain.profile.dto.FriendRequest;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.domain.profile.entity.Friendship;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.domain.profile.entity.constants.FriendStatus;
import com.chinhae.newsfeed.domain.profile.repository.FriendshipRepository;
import com.chinhae.newsfeed.domain.profile.repository.ProfileRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class FriendshipService {

  private final FriendshipRepository repository;
  private final ProfileRepository profileRepository;

  public List<ProfileInfo> getFriends(Long profileId) {
    return repository
        .findAllByToProfileIdAndStatusEquals(profileId, FriendStatus.APPROVED)
        .stream()
        .map(friendship -> ProfileInfo.of(
                profileRepository
                    .findById(friendship.getFromProfile().getId())
                    .orElseThrow()
            )
        )
        .toList();
  }

  // TODO : removeFriends soft delete 구현

  public List<FriendRequest> getFriendRequests(Long profileId) {
    return repository
        .findAllByToProfileIdAndStatusEquals(profileId, FriendStatus.PENDING)
        .stream()
        .map(FriendRequest::of)
        .toList();
  }

  public FriendRequest sendRequest(Long fromProfileId, Long toProfileId) {
    Profile findFromProfile = profileRepository.findById(fromProfileId).orElseThrow();
    Profile findToProfile = profileRepository.findById(toProfileId).orElseThrow();

    // TODO: 친구 요청 전송 예외처리
    Friendship newFriendShip = Friendship.builder()
        .fromProfile(findFromProfile)
        .toProfile(findToProfile)
        .build();
    return FriendRequest.of(newFriendShip);
  }
}
