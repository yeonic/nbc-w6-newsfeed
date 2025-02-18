package com.chinhae.newsfeed.domain.profile.repository;

import com.chinhae.newsfeed.domain.profile.entity.Friendship;
import com.chinhae.newsfeed.domain.profile.entity.constants.FriendStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
  
  List<Friendship> findAllByToProfileIdAndStatusEquals(Long toProfileId, FriendStatus status);
}
