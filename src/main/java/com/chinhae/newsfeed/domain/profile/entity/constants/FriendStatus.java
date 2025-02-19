package com.chinhae.newsfeed.domain.profile.entity.constants;

public enum FriendStatus {
    PENDING, APPROVED, DECLINED, BLOCKED;

    public static boolean isInvalidResponse(FriendStatus status) {
        return status != APPROVED && status != DECLINED;
    }
}
