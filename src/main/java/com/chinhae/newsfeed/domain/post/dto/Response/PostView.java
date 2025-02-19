package com.chinhae.newsfeed.domain.post.dto.Response;

import com.chinhae.newsfeed.domain.comment.dto.reponse.CommentResponseDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostView {

    private PostResponseDto post;
    private List<CommentResponseDto> comments;

    @Builder
    public PostView(PostResponseDto post, List<CommentResponseDto> comments) {
        this.post = post;
        this.comments = comments;
    }
}
