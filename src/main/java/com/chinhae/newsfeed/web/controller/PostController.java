package com.chinhae.newsfeed.web.controller;

import com.chinhae.newsfeed.domain.post.dto.Request.PostRequestDto;
import com.chinhae.newsfeed.domain.post.dto.Response.PostResponseDto;
import com.chinhae.newsfeed.domain.post.service.PostService;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.global.dto.Response;
import com.chinhae.newsfeed.global.messages.SessionKeyConst;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/posts/form")//게시글 생성
    public Response<PostResponseDto> save(@RequestBody PostRequestDto dto,
        @SessionAttribute(name = SessionKeyConst.PROFILE_KEY) ProfileInfo profile) {

        return Response.of(postService.save(dto, profile.getId()));
    }

    @GetMapping("/api/posts")//게시글 전체 조회
    public Response<List<PostResponseDto>> findAll() {
        return Response.of(postService.findAll());
    }

    @GetMapping("/api/posts/{id}")
    public Response<PostResponseDto> findOne(@PathVariable Long id) {
        return Response.of(postService.findById(id));
    }

    @PatchMapping("/api/posts/{id}")
    public Response<PostResponseDto> update(@PathVariable Long id,
        @RequestBody PostRequestDto dto,
        @SessionAttribute(name = SessionKeyConst.PROFILE_KEY) ProfileInfo profile) {
        return Response.of(postService.update(id, dto, profile.getId()));
    }

    @DeleteMapping("/api/posts/{id}")
    public void delete(@PathVariable Long id,
        @SessionAttribute(name = SessionKeyConst.PROFILE_KEY) ProfileInfo profile) {
        postService.deleteById(id, profile.getId());
    }


}
