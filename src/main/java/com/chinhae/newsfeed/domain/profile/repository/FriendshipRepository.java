package com.chinhae.newsfeed.domain.profile.repository;

import com.chinhae.newsfeed.domain.profile.entity.Friendship;
import com.chinhae.newsfeed.domain.profile.entity.constants.FriendStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT f FROM Friendship f WHERE (f.fromProfile.id = :profileId OR f.toProfile.id = :profileId) AND f.status = :status")
    List<Friendship> findAllByProfileIdAndStatus(
        @Param("profileId") Long profileId, @Param("status") FriendStatus status);

    List<Friendship> findAllByToProfileIdAndStatus(Long toProfileId, FriendStatus status);

    Optional<Friendship> findByFromProfileIdAndToProfileId(Long fromProfileId, Long toProfileId);
}
