package com.chinhae.newsfeed.domain.comment.service;

import com.chinhae.newsfeed.domain.base.dto.AuthorDto;
import com.chinhae.newsfeed.domain.comment.dto.reponse.CommentEditResponseDto;
import com.chinhae.newsfeed.domain.comment.dto.reponse.CommentResponseDto;
import com.chinhae.newsfeed.domain.comment.dto.request.CommentRequestDto;
import com.chinhae.newsfeed.domain.comment.entity.Comment;
import com.chinhae.newsfeed.domain.comment.repository.CommentRepository;
import com.chinhae.newsfeed.domain.post.entity.Post;
import com.chinhae.newsfeed.domain.post.repository.PostRepository;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.domain.profile.repository.ProfileRepository;
import com.chinhae.newsfeed.global.messages.CommentConst;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public CommentResponseDto saveComment(Long profileId, Long postId,
        CommentRequestDto requestDto) {

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException(CommentConst.POST_NOT_EXIST));

        Profile profile = profileRepository.findById(profileId)
            .orElseThrow(() -> new IllegalArgumentException(CommentConst.PROFILE_NOT_MATCH));

        Comment comment = new Comment(requestDto.getContent(), profile, post);

        commentRepository.save(comment);

        post.incrementCommentCount();
        postRepository.save(post);

        AuthorDto authorDto = new AuthorDto(profile.getId(), profile.getNickname(),
            profile.getProfileImgUrl());

        return new CommentResponseDto(
            authorDto,
            comment.getContent(),
            comment.getCreatedAt(),
            comment.getUpdatedAt()
        );
    }

    @Transactional
    public List<CommentResponseDto> findByPost(Long postId) {

        List<Comment> comments = commentRepository.findByPost_Id(postId);

        List<CommentResponseDto> dtos = new ArrayList<>();

        for (Comment comment : comments) {
            AuthorDto authorDto = new AuthorDto(
                comment.getProfile().getId(),
                comment.getProfile().getNickname(),
                comment.getProfile().getProfileImgUrl()
            );

            CommentResponseDto responseDto = new CommentResponseDto(authorDto,
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
            );
            dtos.add(responseDto);
        }

        return dtos;
    }

    @Transactional
    public CommentEditResponseDto findOneComment(Long profileId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException(CommentConst.COMMENT_NOT_EXIST));

        if (!comment.getProfile().getId().equals(profileId)) {
            throw new IllegalArgumentException(CommentConst.PROFILE_NOT_MATCH);
        }

        return new CommentEditResponseDto(comment.getContent());
    }

    @Transactional
    public CommentResponseDto updateComment(Long profileId, Long commentId,
        CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException(CommentConst.COMMENT_NOT_EXIST));

        if (!comment.getProfile().getId().equals(profileId)) {
            throw new IllegalArgumentException(CommentConst.PROFILE_NOT_MATCH);
        }

        Profile profile = profileRepository.findById(profileId)
            .orElseThrow(() -> new IllegalArgumentException(CommentConst.PROFILE_NOT_MATCH));

        AuthorDto authorDto = new AuthorDto(profile.getId(), profile.getNickname(),
            profile.getProfileImgUrl());

        comment.update(requestDto.getContent());

        return new CommentResponseDto(
            authorDto,
            comment.getContent(),
            comment.getCreatedAt(),
            comment.getUpdatedAt()
        );
    }

    @Transactional
    public void deleteComment(Long profileId, Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException(CommentConst.COMMENT_NOT_EXIST));

        if (!comment.getProfile().getId().equals(profileId)) {
            throw new IllegalArgumentException(CommentConst.PROFILE_NOT_MATCH);
        }

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException(CommentConst.POST_NOT_EXIST));

        post.decrementCommentCount();
        postRepository.save(post);

        commentRepository.delete(comment);
    }

}
