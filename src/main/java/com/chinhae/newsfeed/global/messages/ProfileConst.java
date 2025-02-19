package com.chinhae.newsfeed.global.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ProfileConst {

    public static final String NICK_REQUIRED = "닉네임은 필수 입력 사항입니다.";
    public static final String WRONG_NICK_FORMAT = "닉네임은 알파벳 대소문자와 허용 문자(._%+-)만 사용 가능합니다.";

    public static final String INVALID_LENGTH = "가능한 길이를 초과했습니다.";
}
