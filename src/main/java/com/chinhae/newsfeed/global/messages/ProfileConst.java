package com.chinhae.newsfeed.global.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ProfileConst {

  public static String NICK_REQUIRED = "닉네임은 필수 입력 사항입니다.";

  public static String ILLEGAL_ACTION = "잘못된 응답 시도입니다.";
}
