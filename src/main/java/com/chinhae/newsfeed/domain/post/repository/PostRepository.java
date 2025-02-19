package com.chinhae.newsfeed.domain.post.repository;

import com.chinhae.newsfeed.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
