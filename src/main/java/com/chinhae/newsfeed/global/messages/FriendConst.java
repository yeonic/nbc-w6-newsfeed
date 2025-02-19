package com.chinhae.newsfeed.global.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class FriendConst {

    public static final String ILLEGAL_ACTION = "잘못된 친구 응답 시도입니다.";

    public static final String REQUEST_BY_MYSELF = "자신에게는 요청할 수 없습니다.";
    public static final String REQUEST_TO_NOBODY = "탈퇴한 회원에게는 요청할 수 없습니다.";
    public static final String REQUEST_TO_FRIEND = "이미 친구인 회원에게는 요청할 수 없습니다.";
    public static final String REQUEST = "요청을 전송할 수 없습니다. ";
}
