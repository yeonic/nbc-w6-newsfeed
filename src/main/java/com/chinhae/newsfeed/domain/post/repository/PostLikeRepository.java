package com.chinhae.newsfeed.domain.post.repository;

import com.chinhae.newsfeed.domain.post.entity.PostLike;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostIdAndProfileId(Long postId, Long profileId);

    Optional<PostLike> findByPostIdAndProfileId(Long postId, Long profileId);

    Integer countPostLikeByPostId(Long postId);

    Page<PostLike> findAllByProfileId(Long profileId, Pageable pageable);
}
