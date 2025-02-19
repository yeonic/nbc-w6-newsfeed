package com.chinhae.newsfeed.domain.profile.repository;

import com.chinhae.newsfeed.domain.profile.entity.Profile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findAllByAccountId(Long accountId);

    @Query("SELECT COUNT(1) FROM Friendship f WHERE f.status='APPROVED' AND (f.fromProfile.id=:profileId OR f.toProfile.id=:profileId)")
    Integer countFriendsByProfileId(@Param("profileId") Long profileId);

    @Query("SELECT COUNT(1) FROM Post p WHERE p.profile.id=:profileId")
    Integer countPostsByProfileId(@Param("profileId") Long profileId);
}
