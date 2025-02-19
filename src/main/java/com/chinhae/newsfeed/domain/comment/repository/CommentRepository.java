package com.chinhae.newsfeed.domain.comment.repository;

import com.chinhae.newsfeed.domain.comment.entity.Comment;
import com.chinhae.newsfeed.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_Id(Long postId);
    List<Long> post(Post post);
}
