package com.chinhae.newsfeed.global.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class LoginConst {
    public static final String LOGIN_FAILED_MESSAGE = "로그인 실패했습니다.";
    public static final String DELETE_FAILED_MESSAGE = "회원 탈퇴가 실패했습니다.";
    public static final String EMAIL_EXIST_MESSAGE = "현재 이메일은 존재합니다.";
    public static final String USER_NOTEXIST_MESSAGE = "사용자가 존재하지않습니다.";
    public static final String PASSWORD_NOT_MATCH = "비밀번호가 틀렸습니다.";
}
