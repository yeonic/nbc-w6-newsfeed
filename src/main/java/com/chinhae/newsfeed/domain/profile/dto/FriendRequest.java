package com.chinhae.newsfeed.domain.profile.dto;

import com.chinhae.newsfeed.domain.profile.entity.Friendship;
import com.chinhae.newsfeed.domain.profile.entity.constants.FriendStatus;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FriendRequest {

    private Long id;
    private Long fromProfileId;
    private Long toProfileId;
    private FriendStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public FriendRequest(Long id, Long fromProfileId, Long toProfileId, FriendStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        this.id = id;
        this.fromProfileId = fromProfileId;
        this.toProfileId = toProfileId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FriendRequest of(Friendship friendship) {
        return FriendRequest.builder()
            .id(friendship.getId())
            .fromProfileId(friendship.getFromProfile().getId())
            .toProfileId(friendship.getToProfile().getId())
            .status(friendship.getStatus())
            .createdAt(friendship.getCreatedAt())
            .updatedAt(friendship.getUpdatedAt())
            .build();
    }
}
