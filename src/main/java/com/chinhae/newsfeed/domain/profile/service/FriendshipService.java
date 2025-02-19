package com.chinhae.newsfeed.domain.profile.service;

import com.chinhae.newsfeed.domain.profile.dto.FriendRequest;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.domain.profile.entity.Friendship;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.domain.profile.entity.constants.FriendStatus;
import com.chinhae.newsfeed.domain.profile.exception.IllegalFriendResponseException;
import com.chinhae.newsfeed.domain.profile.repository.FriendshipRepository;
import com.chinhae.newsfeed.domain.profile.repository.ProfileRepository;
import com.chinhae.newsfeed.global.messages.ProfileConst;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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

    // TODO : deleteFriend보다는 blockFriend가 자연스러움
//    public void deleteFriend(Long currenProfileId, Long friendId) {
//
//    }

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
        // 보내는 대상이 나인지 확인
        // 탈퇴한 회원인지 확인
        // 이미 친구인지 확인
        // Pending인지
        Friendship newFriendShip = Friendship.builder()
            .fromProfile(findFromProfile)
            .toProfile(findToProfile)
            .build();
        return FriendRequest.of(repository.save(newFriendShip));
    }

    public void respondRequest(Long requestId, FriendStatus status) {
        if (FriendStatus.isInvalidResponse(status)) {
            throw new IllegalFriendResponseException(ProfileConst.ILLEGAL_ACTION);
        }
        Friendship friendship = repository.findById(requestId).orElseThrow();
        friendship.changeStatus(status);
    }
}
