package com.chinhae.newsfeed.domain.comment.repository;

import com.chinhae.newsfeed.domain.comment.dto.CommentCountDto;
import com.chinhae.newsfeed.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_Id(Long postId);

    @Query(
        "select new com.chinhae.newsfeed.domain.comment.dto.CommentCountDto(c.post.id, count(c)) " +
            "from Comment c " +
            "where c.post.id in :posts " +
            "group by c.post.id"
    )
    List<CommentCountDto> countByPostIds(List<Long> posts);
}
