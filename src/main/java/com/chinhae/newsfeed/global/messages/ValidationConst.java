package com.chinhae.newsfeed.global.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ValidationConst {

    public static final String WRONG_PASSWORD = "비밀번호는 8~16자, 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
    public static final String EMAIL_IS_WRONG = "이메일을 입력해주세요.";

    public static final String INVALID_PAGENUM = "페이지 번호는 음수일 수 없습니다.";
}
