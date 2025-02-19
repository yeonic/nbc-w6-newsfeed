package com.chinhae.newsfeed.domain.base.dto;

import lombok.Getter;

@Getter
public class AuthorDto {

    private final Long profileId;

    private final String nickName;

    private final String profileImgUrl;


    public AuthorDto(Long profileId, String nickName, String profileImgUrl) {
        this.profileId = profileId;
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
    }
}
