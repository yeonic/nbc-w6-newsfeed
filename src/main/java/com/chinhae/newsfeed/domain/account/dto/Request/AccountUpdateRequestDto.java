package com.chinhae.newsfeed.domain.account.dto.Request;

import lombok.Getter;

@Getter

public class AccountUpdateRequestDto {
    private String currentPassword;
    private String newPassword;
    private String bio;
    private String profileImgUrl;
}
