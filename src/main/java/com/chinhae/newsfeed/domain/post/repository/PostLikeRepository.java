package com.chinhae.newsfeed.domain.post.repository;

import com.chinhae.newsfeed.domain.post.entity.PostLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostIdAndProfileId(Long postId, Long profileId);

    Optional<PostLike> findByPostIdAndProfileId(Long postId, Long profileId);

    Integer countPostLikeByPostId(Long postId);

    List<PostLike> findAllByProfileId(Long profileId);
}
