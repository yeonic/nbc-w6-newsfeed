package com.chinhae.newsfeed.domain.post.service;

import com.chinhae.newsfeed.domain.post.dto.AuthorDto;
import com.chinhae.newsfeed.domain.post.dto.Request.PostRequestDto;
import com.chinhae.newsfeed.domain.post.dto.Response.PostResponseDto;
import com.chinhae.newsfeed.domain.post.entity.Post;
import com.chinhae.newsfeed.domain.post.repository.PostRepository;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;


    @Transactional
    public PostResponseDto save(PostRequestDto dto, Long profileId) {

        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 사용자 id입니다.")
        );


        Post post = new Post(profile, dto.getContent());

        Post savedPost = postRepository.save(post);
        //AuthorDto 객체 생성...
        AuthorDto author = new AuthorDto( profile.getId(),
                profile.getNickname(), profile.getProfileImgUrl());



        return new PostResponseDto(savedPost.getId(), savedPost.getContent(),
                author, savedPost.getLikeCount(), savedPost.getCommentCount(),
                savedPost.getViewCount(), savedPost.getCreated_at(), savedPost.getUpdated_at());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();

        List<PostResponseDto> dtos = new ArrayList<>();

        for (Post post : posts) {
            //AuthorDto 객체 생성...
            AuthorDto author = new AuthorDto( post.getProfile().getId(),
                    post.getProfile().getNickname(),post.getProfile().getProfileImgUrl());

            PostResponseDto dto = new PostResponseDto(post.getId(), post.getContent(),
                    author, post.getLikeCount(), post.getCommentCount(), post.getViewCount(),
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
        //AuthorDto 객체 생성...
        AuthorDto author = new AuthorDto( post.getProfile().getId(),
                post.getProfile().getNickname(),post.getProfile().getProfileImgUrl());

        return new PostResponseDto(post.getId(), post.getContent(),
                author, post.getLikeCount(), post.getCommentCount(), post.getViewCount(),
                post.getCreated_at(), post.getUpdated_at());
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id를 조회할 수 없습니다.")
        );

        post.update(dto.getContent());

        //AuthorDto 객체 생성...
        AuthorDto author = new AuthorDto( post.getProfile().getId(),
                post.getProfile().getNickname(),post.getProfile().getProfileImgUrl());

        return new PostResponseDto(post.getId(), post.getContent(),
                author, post.getLikeCount(), post.getCommentCount(), post.getViewCount(),
                post.getCreated_at(), post.getUpdated_at());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 id가 존재하지 않아 삭제가 불가능 합니다.");
        }
        postRepository.deleteById(id);
    }
}
