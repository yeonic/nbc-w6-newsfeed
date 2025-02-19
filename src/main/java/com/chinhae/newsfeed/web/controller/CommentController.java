package com.chinhae.newsfeed.web.controller;

import com.chinhae.newsfeed.domain.comment.dto.reponse.CommentEditResponseDto;
import com.chinhae.newsfeed.domain.comment.dto.reponse.CommentResponseDto;
import com.chinhae.newsfeed.domain.comment.dto.request.CommentRequestDto;
import com.chinhae.newsfeed.domain.comment.service.CommentService;
import com.chinhae.newsfeed.domain.profile.dto.ProfileInfo;
import com.chinhae.newsfeed.global.dto.Response;
import com.chinhae.newsfeed.global.messages.SessionKeyConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments/add") // 댓글 생성
    @ResponseStatus(HttpStatus.CREATED)
    public Response<CommentResponseDto> save(
            @SessionAttribute(name = SessionKeyConst.PROFILE_KEY) ProfileInfo profile,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto requestDto
    ){
        Long profileId = profile.getId();
        return Response.of(commentService.saveComment(profileId, postId, requestDto));
    }

    @GetMapping("/api/posts/{postId}/comments") // 댓글 전체 조회
    public Response<List<CommentResponseDto>> findAll(@PathVariable Long postId){
        return Response.of(commentService.findByPost(postId));
    }

    @GetMapping("/api/posts/{postId}/comments/{commentId}/edit") // 댓글 수정 폼
    public Response<CommentEditResponseDto> editForm(
            @SessionAttribute(name = SessionKeyConst.PROFILE_KEY) ProfileInfo profile,
            @PathVariable Long commentId
    ) {
        Long profileId = profile.getId();
        return Response.of(commentService.findOneComment(profileId, commentId));
    }

    @PatchMapping("/api/posts/{postId}/comments/{commentId}/edit") // 댓글 수정
    public Response<CommentResponseDto> update(
            @SessionAttribute(name = SessionKeyConst.PROFILE_KEY) ProfileInfo profile,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto
    ){
        Long profileId = profile.getId();
        return Response.of(commentService.updateComment(profileId, commentId, requestDto));
    }

    @DeleteMapping("/api/posts/{postId}/comments/{commentId}") // 댓글 삭제
    public Response<String> delete (
            @SessionAttribute(name = SessionKeyConst.PROFILE_KEY) ProfileInfo profile,
            @PathVariable Long postId,
            @PathVariable Long commentId
    ){
        Long profileId = profile.getId();
        commentService.deleteComment(profileId, postId, commentId);
        return Response.of("댓글이 삭제되었습니다.");
    }
}
