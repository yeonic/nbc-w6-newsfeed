package com.chinhae.newsfeed.web.controller;

import com.chinhae.newsfeed.domain.profile.dto.FriendRequest;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.domain.profile.entity.constants.FriendStatus;
import com.chinhae.newsfeed.domain.profile.service.FriendshipService;
import com.chinhae.newsfeed.global.dto.Response;
import com.chinhae.newsfeed.global.messages.SessionKeyConst;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profiles/")
public class FriendshipController {

    private final FriendshipService service;

    @GetMapping("/{profileId}/friends")
    public Response<List<ProfileInfo>> friends(@PathVariable("profileId") Long profileId) {
        return Response.of(service.getFriends(profileId));
    }

//    @DeleteMapping("/friends/{friendId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void removeFriend(@PathVariable("friendId") Long friendId) {
//
//    }

    @GetMapping("/friend-requests")
    public Response<List<FriendRequest>> friendRequests(
        @SessionAttribute(name = SessionKeyConst.PROFILE_KEY) ProfileInfo currentProfile
    ) {
        return Response.of(service.getFriendRequests(currentProfile.getId()));
    }

    @PostMapping("/friend-requests/{friendId}")
    public Response<FriendRequest> sendRequest(
        @SessionAttribute(name = SessionKeyConst.PROFILE_KEY) ProfileInfo currentProfile,
        @PathVariable("friendId") Long friendId
    ) {
        return Response.of(service.sendRequest(currentProfile.getId(), friendId));
    }

    @PatchMapping("/friend-requests/{requestId}")
    public void respondRequest(
        @SessionAttribute(name = SessionKeyConst.PROFILE_KEY) ProfileInfo currentProfile,
        @PathVariable("requestId") Long requestId,
        @RequestParam("actions") FriendStatus status
    ) {
        log.info("input status = {}", status.toString());
        service.respondRequest(requestId, currentProfile.getId(), status);
    }
}
