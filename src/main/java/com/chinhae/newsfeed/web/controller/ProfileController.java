package com.chinhae.newsfeed.web.controller;

import com.chinhae.newsfeed.domain.account.dto.Response.AccountResponseDto;
import com.chinhae.newsfeed.domain.profile.dto.ProfileForm;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.domain.profile.dto.ProfileView;
import com.chinhae.newsfeed.domain.profile.service.ProfileService;
import com.chinhae.newsfeed.global.dto.Response;
import com.chinhae.newsfeed.global.messages.SessionKeyConst;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService service;

    // TODO: 검색 기능 구현

    @PostMapping("/form")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<ProfileInfo> addProfile(
        @SessionAttribute(name = SessionKeyConst.ACCOUNT_KEY) AccountResponseDto account,
        @RequestBody ProfileForm form, HttpServletRequest request) {
        log.info("account = {}", request.getAttribute(SessionKeyConst.ACCOUNT_KEY));
        return Response.of(service.addProfile(account.getId(), form));
    }

    @GetMapping("/{profileId}")
    public Response<ProfileView> profile(@PathVariable(name = "profileId") Long profileId) {
        return Response.of(service.getProfile(profileId));
    }

    @GetMapping("/{profileId}/settings")
    public Response<ProfileForm> editForm(@PathVariable(name = "profileId") Long profileId) {
        return Response.of(service.getForm(profileId));
    }

    @PatchMapping("/{profileId}/settings")
    public Response<ProfileInfo> edit(
        @PathVariable(name = "profileId") Long profileId,
        @RequestBody ProfileForm form
    ) {
        return Response.of(service.update(profileId, form));
    }

    @DeleteMapping("/{profileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "profileId") Long profileId) {
        service.delete(profileId);
    }
}
