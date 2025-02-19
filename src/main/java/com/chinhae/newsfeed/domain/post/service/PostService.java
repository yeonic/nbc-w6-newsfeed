package com.chinhae.newsfeed.domain.post.service;

import com.chinhae.newsfeed.domain.base.dto.AuthorDto;
import com.chinhae.newsfeed.domain.comment.dto.CommentCountDto;
import com.chinhae.newsfeed.domain.comment.repository.CommentRepository;
import com.chinhae.newsfeed.domain.post.dto.Request.PostRequestDto;
import com.chinhae.newsfeed.domain.post.dto.Response.PostResponseDto;
import com.chinhae.newsfeed.domain.post.entity.Post;
import com.chinhae.newsfeed.domain.post.repository.PostRepository;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.domain.profile.repository.ProfileRepository;
import com.chinhae.newsfeed.global.messages.PostConst;
import com.chinhae.newsfeed.web.interceptor.exception.UnauthorizedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;
    private final CommentRepository commentRepository;


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
            savedPost.getViewCount(), savedPost.getCreated_at(), savedPost.getUpdated_at());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();

        List<PostResponseDto> dtos = new ArrayList<>();

        List<Long> postIds = posts.stream()
                .map(Post::getId)
                .toList();

        List<CommentCountDto> countResults = commentRepository.countByPostIds(postIds);

        Map<Long, Long> commentCountMap = countResults.stream()
                .collect(Collectors.toMap(CommentCountDto::getPostId, CommentCountDto::getCount));

        for (Post post : posts) {
            //AuthorDto 객체 생성...
            AuthorDto author = new AuthorDto(post.getProfile().getId(),
                post.getProfile().getNickname(), post.getProfile().getProfileImgUrl());

            PostResponseDto dto = new PostResponseDto(post.getId(), post.getContent(),
                author, post.getLikeCount(), commentCountMap.get(post.getId()).intValue(), post.getViewCount(),
                post.getCreated_at(), post.getUpdated_at());
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("해당 id를 조회할 수 없습니다.")
        );

        Long commentCount = commentRepository.countCommentByPost_Id(post.getId());

        //AuthorDto 객체 생성...
        AuthorDto author = new AuthorDto(post.getProfile().getId(),
            post.getProfile().getNickname(), post.getProfile().getProfileImgUrl());

        return new PostResponseDto(post.getId(), post.getContent(),
            author, post.getLikeCount(), commentCount.intValue(), post.getViewCount(),
            post.getCreated_at(), post.getUpdated_at());
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

        Long commentCount = commentRepository.countCommentByPost_Id(post.getId());

        //AuthorDto 객체 생성...
        AuthorDto author = new AuthorDto(post.getProfile().getId(),
            post.getProfile().getNickname(), post.getProfile().getProfileImgUrl());

        return new PostResponseDto(post.getId(), post.getContent(),
            author, post.getLikeCount(), commentCount.intValue(), post.getViewCount(),
            post.getCreated_at(), post.getUpdated_at());
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
}
