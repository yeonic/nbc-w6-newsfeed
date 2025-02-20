package com.chinhae.newsfeed.domain.profile.service;

import com.chinhae.newsfeed.domain.profile.dto.FriendRequest;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.domain.profile.entity.Friendship;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.domain.profile.entity.constants.FriendStatus;
import com.chinhae.newsfeed.domain.profile.exception.IllegalFriendWorkException;
import com.chinhae.newsfeed.domain.profile.repository.FriendshipRepository;
import com.chinhae.newsfeed.domain.profile.repository.ProfileRepository;
import com.chinhae.newsfeed.global.messages.FriendConst;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class FriendshipService {

    private final FriendshipRepository repository;
    private final ProfileRepository profileRepository;

    public List<ProfileInfo> getFriends(Long profileId, FriendStatus status) {
        return repository
            .findAllByProfileIdAndStatus(profileId, status)
            .stream()
            .map(friendship -> {
                    Long friendId = getFriendId(profileId, friendship);

                    return ProfileInfo.of(
                        profileRepository
                            .findById(friendId)
                            .orElseThrow()
                    );
                }
            )
            .toList();
    }

    public void blockFriend(Long currenProfileId, Long friendProfileId) {
        // 보내는 대상과 받는 대상이 같은지 확인
        if (currenProfileId.equals(friendProfileId)) {
            throw new IllegalFriendWorkException(FriendConst.REQUEST_BY_MYSELF);
        }

        Optional<Friendship> findFriendship = repository.findByFromProfileIdAndToProfileId(
            currenProfileId,
            friendProfileId);

        findFriendship.ifPresentOrElse(
            (friendship) -> friendship.changeStatus(FriendStatus.BLOCKED),
            () -> {
                Friendship blockRel = Friendship.builder()
                    .fromProfile(profileRepository.findById(currenProfileId).orElseThrow())
                    .toProfile(profileRepository.findById(friendProfileId).orElseThrow())
                    .build();

                // 탈퇴한 회원인지 확인
                if (isDeletedProfile(blockRel.getFromProfile(), blockRel.getToProfile())) {
                    throw new IllegalFriendWorkException(FriendConst.REQUEST_TO_NOBODY);
                }

                blockRel.changeStatus(FriendStatus.BLOCKED);
            });
    }

    public List<FriendRequest> getFriendRequests(Long profileId) {
        return repository
            .findAllByToProfileIdAndStatus(profileId, FriendStatus.PENDING)
            .stream()
            .filter(friendship -> {
                Optional<Friendship> reversedFriendShip = repository.findByFromProfileIdAndToProfileId(
                    friendship.getToProfile().getId(), friendship.getFromProfile().getId());
                return !isFriendShipStatus(reversedFriendShip, FriendStatus.BLOCKED);
            })
            .map(FriendRequest::of)
            .toList();
    }

    public FriendRequest sendRequest(Long fromProfileId, Long toProfileId) {

        // 보내는 대상과 받는 대상이 같은지 확인
        if (fromProfileId.equals(toProfileId)) {
            throw new IllegalFriendWorkException(FriendConst.REQUEST_BY_MYSELF);
        }

        Profile findFromProfile = profileRepository.findById(fromProfileId).orElseThrow();
        Profile findToProfile = profileRepository.findById(toProfileId).orElseThrow();

        // 탈퇴한 회원인지 확인
        if (isDeletedProfile(findFromProfile, findToProfile)) {
            throw new IllegalFriendWorkException(FriendConst.REQUEST_TO_NOBODY);
        }

        Optional<Friendship> findFriendship = repository
            .findByFromProfileIdAndToProfileId(fromProfileId, toProfileId);
        Optional<Friendship> reversedFriendship = repository
            .findByFromProfileIdAndToProfileId(toProfileId, fromProfileId);

        // 이미 친구인지 확인
        if (isAlreadyFriend(findFriendship, reversedFriendship)) {
            throw new IllegalFriendWorkException(FriendConst.REQUEST_TO_FRIEND);
        }

        // Pending인 요청이 있는지 확인
        if (isAlreadyPending(findFriendship, reversedFriendship)) {
            throw new IllegalFriendWorkException(FriendConst.REQUEST);
        }

        Friendship newFriendShip = Friendship.builder()
            .fromProfile(findFromProfile)
            .toProfile(findToProfile)
            .build();
        return FriendRequest.of(repository.save(newFriendShip));
    }

    public void respondRequest(Long requestId, Long currentProfileId, FriendStatus status) {
        if (FriendStatus.isInvalidResponse(status)) {
            throw new IllegalFriendWorkException(FriendConst.ILLEGAL_ACTION);
        }
        Friendship friendship = repository.findById(requestId).orElseThrow();

        if (friendship.getStatus() != FriendStatus.PENDING) {
            throw new IllegalFriendWorkException(FriendConst.ILLEGAL_ACTION);
        }

        if (isRequestRobbery(currentProfileId, friendship)) {
            throw new IllegalFriendWorkException(FriendConst.ILLEGAL_ACTION);
        }

        friendship.changeStatus(status);
    }


    private Long getFriendId(Long profileId, Friendship friendship) {
        return profileId.equals(friendship.getFromProfile().getId())
            ? friendship.getToProfile().getId()
            : friendship.getFromProfile().getId();
    }


    private boolean isRequestRobbery(Long currentProfileId, Friendship friendship) {
        return !friendship.getToProfile().getId().equals(currentProfileId);
    }

    private boolean isAlreadyPending(
        Optional<Friendship> findFriendship,
        Optional<Friendship> reversedFriendship
    ) {
        return isFriendShipStatus(findFriendship, FriendStatus.PENDING) ||
            isFriendShipStatus(reversedFriendship, FriendStatus.PENDING);
    }

    private boolean isAlreadyFriend(
        Optional<Friendship> friendship,
        Optional<Friendship> reversedFriendship
    ) {
        return isFriendShipStatus(friendship, FriendStatus.APPROVED) ||
            isFriendShipStatus(reversedFriendship, FriendStatus.APPROVED);
    }

    private boolean isFriendShipStatus(Optional<Friendship> friendship, FriendStatus status) {
        return friendship.isPresent() && friendship.get().getStatus() == status;
    }

    private boolean isDeletedProfile(Profile findFromProfile, Profile findToProfile) {
        return findFromProfile.getAccount().isDeleted() || findToProfile.getAccount().isDeleted();
    }
}
