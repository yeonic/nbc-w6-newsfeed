package com.chinhae.newsfeed.domain.post.controller;

import com.chinhae.newsfeed.domain.post.dto.Request.PostRequestDto;
import com.chinhae.newsfeed.domain.post.dto.Response.PostResponseDto;
import com.chinhae.newsfeed.domain.post.service.PostService;
import com.chinhae.newsfeed.domain.profile.entity.Profile;
import com.chinhae.newsfeed.global.dto.Response;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.authenticator.SingleSignOnSessionKey;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/posts")//게시글 생성
    public Response<PostResponseDto> save (@RequestBody PostRequestDto dto) {
        //@SessionAttribute(name = "profileId", required = false) Long profileId

        return Response.of(postService.save(dto, 1L));
    }

    @GetMapping("/api/posts")//게시글 전체 조회
    public Response<List<PostResponseDto>> findAll () {
        return Response.of(postService.findAll());
    }

    @GetMapping("/api/posts/{id}")
    public Response<PostResponseDto>  findOne (@PathVariable Long id) {
        return Response.of(postService.findById(id));
    }

    @PutMapping("/api/posts/{id}")
    public Response<PostResponseDto>  update (@PathVariable Long id, @RequestBody PostRequestDto dto) {
        return Response.of(postService.update(id, dto));
    }

    @DeleteMapping("/api/posts/{id}")
    public void delete (@PathVariable Long id) {
        postService.deleteById(id);
    }



}
