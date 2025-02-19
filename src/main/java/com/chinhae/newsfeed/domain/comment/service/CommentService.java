package com.chinhae.newsfeed.domain.comment.service;

import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.domain.profile.repository.ProfileRepository;
import com.chinhae.newsfeed.global.messages.CommentConst;
import com.chinhae.newsfeed.domain.comment.dto.reponse.CommentEditResponseDto;
import com.chinhae.newsfeed.domain.comment.dto.reponse.CommentResponseDto;
import com.chinhae.newsfeed.domain.comment.dto.request.CommentRequestDto;
import com.chinhae.newsfeed.domain.comment.entity.Comment;
import com.chinhae.newsfeed.domain.comment.repository.CommentRepository;
import com.chinhae.newsfeed.none.post.entity.Post;
import com.chinhae.newsfeed.none.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public CommentResponseDto saveComment(Long profileId, Long postId, CommentRequestDto requestDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(CommentConst.COMMENT_NOT_EXIST));

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException(CommentConst.PROFILE_NOT_MATCH));

        Comment comment = new Comment(requestDto.getContent(), profile, post);

        commentRepository.save(comment);

        return new CommentResponseDto(
                profile.getId(),
                profile.getNickname(),
                profile.getProfileImgUrl(),
                comment.getContent(),
                comment.getCreated_at(),
                comment.getUpdated_at()
        );
    }

    @Transactional
    public List<CommentResponseDto> findByPost(Long postId) {

        List<Comment> comments = commentRepository.findByPost_Id(postId);

        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getProfile().getId(),
                        comment.getProfile().getNickname(),
                        comment.getProfile().getProfileImgUrl(),
                        comment.getContent(),
                        comment.getCreated_at(),
                        comment.getUpdated_at()))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentEditResponseDto findOneComment(Long profileId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException(CommentConst.COMMENT_NOT_EXIST));

        if(!comment.getProfile().getId().equals(profileId)) {
            throw new IllegalArgumentException(CommentConst.PROFILE_NOT_MATCH);
        }

        return new CommentEditResponseDto(comment.getContent());
    }

    @Transactional
    public CommentResponseDto updateComment(Long profileId, Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException(CommentConst.COMMENT_NOT_EXIST));

        if(!comment.getProfile().getId().equals(profileId)) {
            throw new IllegalArgumentException(CommentConst.PROFILE_NOT_MATCH);
        }

        comment.update(requestDto.getContent());

        return new CommentResponseDto(
                comment.getProfile().getId(),
                comment.getProfile().getNickname(),
                comment.getProfile().getProfileImgUrl(),
                comment.getContent(),
                comment.getCreated_at(),
                comment.getUpdated_at()
        );
    }

    @Transactional
    public void deleteComment(Long profileId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException(CommentConst.COMMENT_NOT_EXIST));

        if(!comment.getProfile().getId().equals(profileId)) {
            throw new IllegalArgumentException(CommentConst.PROFILE_NOT_MATCH);
        }

        commentRepository.delete(comment);
    }

}
