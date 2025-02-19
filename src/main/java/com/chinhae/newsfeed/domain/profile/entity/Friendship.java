package com.chinhae.newsfeed.domain.profile.entity;

import com.chinhae.newsfeed.domain.base.entity.BaseEntity;
import com.chinhae.newsfeed.domain.profile.entity.constants.FriendStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Friendship extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_profile_id")
    private Profile fromProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_profile_id")
    private Profile toProfile;

    @Enumerated(EnumType.STRING)
    private FriendStatus status = FriendStatus.PENDING;

    @Builder
    public Friendship(
        Profile fromProfile, Profile toProfile
    ) {
        this.fromProfile = fromProfile;
        this.toProfile = toProfile;
    }

    public void changeStatus(FriendStatus newStatus) {
        this.status = newStatus;
    }
}
