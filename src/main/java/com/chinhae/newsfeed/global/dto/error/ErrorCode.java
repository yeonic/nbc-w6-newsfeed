package com.chinhae.newsfeed.global.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // common -> Spring과 Custom exception
    VALIDATION("COMMON_001", "Input validation failed"),
    AUTHORIZAION("COMMON_002", "Unauthorized access"),
    ILLEGAL_FRIEND_TASK("COMMON_003", "Bad tries upon friendships"),

    // standard -> Java에서 제공하는 Exception
    NO_SUCH_ELEMENT("STANDARD_001", "Cannot find element"),
    ILLEGAL_ARGUMENT("STANDARD_002", "Cannot find element"),

    // finally
    EXCEPTION("EXCEPTION", "Uncaught exception");

    private final String code;
    private final String message;
}
