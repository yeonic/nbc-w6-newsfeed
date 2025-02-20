package com.chinhae.newsfeed.domain.post.repository;

import com.chinhae.newsfeed.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByProfileId(Long profileId, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
}
