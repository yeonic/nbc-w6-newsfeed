package com.chinhae.newsfeed.domain.post.service;

import com.chinhae.newsfeed.domain.base.dto.AuthorDto;
import com.chinhae.newsfeed.domain.comment.dto.reponse.CommentResponseDto;
import com.chinhae.newsfeed.domain.comment.service.CommentService;
import com.chinhae.newsfeed.domain.post.dto.Request.PostRequestDto;
import com.chinhae.newsfeed.domain.post.dto.Response.PostResponseDto;
import com.chinhae.newsfeed.domain.post.dto.Response.PostView;
import com.chinhae.newsfeed.domain.post.entity.Post;
import com.chinhae.newsfeed.domain.post.entity.PostLike;
import com.chinhae.newsfeed.domain.post.repository.PostLikeRepository;
import com.chinhae.newsfeed.domain.post.repository.PostRepository;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.domain.profile.repository.ProfileRepository;
import com.chinhae.newsfeed.global.dto.paging.PagingData;
import com.chinhae.newsfeed.global.messages.PostConst;
import com.chinhae.newsfeed.web.interceptor.exception.UnauthorizedException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final ProfileRepository profileRepository;
    private final CommentService commentService;


    @Transactional
    public PostResponseDto save(PostRequestDto dto, Long profileId) {

        Profile profile = profileRepository.findById(profileId).orElseThrow(
            () -> new IllegalArgumentException("잘못된 사용자 id입니다.")
        );

        Post post = new Post(profile, dto.getContent());

        Post savedPost = postRepository.save(post);
        //AuthorDto 객체 생성...
        AuthorDto author = new AuthorDto(profile.getId(),
            profile.getNickname(), profile.getProfileImgUrl());

        return new PostResponseDto(savedPost.getId(), savedPost.getContent(),
            author, savedPost.getLikeCount(), savedPost.getCommentCount(),
            savedPost.getViewCount(), savedPost.getCreatedAt(), savedPost.getUpdatedAt());
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAll(Long profileId, PagingData pagingData) {
        Pageable pageable = PagingData.toPageRequest(pagingData);

        Page<Post> findPage = postRepository.findAll(pageable);

        return findPage.map(post -> {
            boolean isLiked = false;
            if (profileId != null) {
                isLiked = postLikeRepository.existsByPostIdAndProfileId(post.getId(), profileId);
            }
            return PostResponseDto.of(post, isLiked);
        });
    }

    @Transactional(readOnly = true)
    public PostView findById(Long id, Long profileId) {

        Post post = postRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("해당 id를 조회할 수 없습니다.")
        );

        boolean isLiked = false;
        if (profileId != null) {
            isLiked = postLikeRepository.existsByPostIdAndProfileId(id, profileId);
        }

        List<CommentResponseDto> comments = commentService.findByPost(id);

        //AuthorDto 객체 생성...
        AuthorDto author = new AuthorDto(post.getProfile().getId(),
            post.getProfile().getNickname(), post.getProfile().getProfileImgUrl());

        post.updateViewCount(post.getViewCount());//조회수 count

        PostResponseDto posts = new PostResponseDto(post.getId(), post.getContent(),
            author, post.getLikeCount(), post.getCommentCount(), post.getViewCount(), isLiked,
            post.getCreatedAt(), post.getUpdatedAt());

        return PostView.builder()
            .post(posts)
            .comments(comments)
            .build();
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllByProfileId(Long profileId, PagingData pagingData) {
        Pageable pageable = PagingData.toPageRequest(pagingData);
        return postRepository.findAllByProfileId(profileId, pageable).map(PostResponseDto::of);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllLikedPosts(Long profileId, PagingData pagingData) {
        Pageable pageable = PagingData.toPageRequest(pagingData);

        return postLikeRepository.findAllByProfileId(profileId, pageable)
            .map(postLike -> PostResponseDto.of(postLike.getPost(), true));

    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto dto, Long profileId) {
        Post post = postRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("해당 id를 조회할 수 없습니다.")
        );
        if (!post.getProfile().getId().equals(profileId)) {
            throw new UnauthorizedException(PostConst.BAD_ACCESS);
        }

        post.update(dto.getContent());

        //AuthorDto 객체 생성...
        AuthorDto author = new AuthorDto(post.getProfile().getId(),
            post.getProfile().getNickname(), post.getProfile().getProfileImgUrl());

        return new PostResponseDto(post.getId(), post.getContent(),
            author, post.getLikeCount(), post.getCommentCount(), post.getViewCount(),
            post.getCreatedAt(), post.getUpdatedAt());
    }

    @Transactional
    public void deleteById(Long id, Long profileId) {
        Post post = postRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("해당 id가 존재하지 않아 삭제가 불가능 합니다.")
        );

        if (!post.getProfile().getId().equals(profileId)) {
            throw new UnauthorizedException(PostConst.BAD_ACCESS);
        }
        postRepository.deleteById(id);
    }

    @Transactional
    public void doLike(Long postId, Long profileId) {
        if (postLikeRepository.existsByPostIdAndProfileId(postId, profileId)) {
            throw new IllegalStateException(PostConst.BAD_ACCESS);
        }

        Post findPost = postRepository.findById(postId).orElseThrow();
        Profile findProfile = profileRepository.findById(profileId).orElseThrow();

        postLikeRepository.save(
            PostLike.builder()
                .post(findPost)
                .profile(findProfile)
                .build());

        // update like count
        findPost.updateLikesCount(postLikeRepository.countPostLikeByPostId(findPost.getId()));
    }

    @Transactional
    public void undoLike(Long postId, Long profileId) {
        PostLike findPostLike = postLikeRepository.findByPostIdAndProfileId(postId, profileId)
            .orElseThrow(() -> new IllegalArgumentException(PostConst.BAD_ACCESS));
        postLikeRepository.delete(findPostLike);

        // update like count
        Post post = findPostLike.getPost();
        post.updateLikesCount(postLikeRepository.countPostLikeByPostId(post.getId()));
    }
}
