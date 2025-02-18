package com.chinhae.newsfeed.web.controller;

import com.chinhae.newsfeed.domain.profile.dto.FriendRequest;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.domain.profile.service.FriendshipService;
import com.chinhae.newsfeed.global.dto.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profiles/{profileId}")
public class FriendshipController {

  private final FriendshipService service;

  @GetMapping("/friends")
  public Response<List<ProfileInfo>> friends(@PathVariable("profileId") Long profileId) {
    return Response.of(service.getFriends(profileId));
  }


  @DeleteMapping("/friends/{friendId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeFriend(
      @PathVariable("profileId") Long profileId,
      @PathVariable("friendId") Long friendId
  ) {

  }

  @GetMapping("/friend-requests")
  public Response<List<FriendRequest>> friendRequests(@PathVariable("profileId") Long profileId) {
    return Response.of(service.getFriendRequests(profileId));
  }
//
//  @PostMapping("/friend-requests")
//  public Response<FriendRequest> sendRequest(
//      @PathVariable("profileId") Long profileId
//  ) {
//    return Response.of(service.sendRequest());
//  }
//
//  @PatchMapping("/friend-requests/{requestId}")
//  public void respondRequest(
//      @PathVariable("profileId") Long profileId,
//      @PathVariable("requestId") Long requestId,
//      @RequestParam FriendStatus status
//  ) {
//
//  }


}
