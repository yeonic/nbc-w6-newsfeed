package com.chinhae.newsfeed.domain.post.repository;

import com.chinhae.newsfeed.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByProfileId(Long profileId);
}
