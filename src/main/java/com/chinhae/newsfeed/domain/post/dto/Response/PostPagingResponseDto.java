package com.chinhae.newsfeed.domain.post.dto.Response;

import com.chinhae.newsfeed.global.dto.paging.PagingData;
import com.chinhae.newsfeed.global.dto.paging.PagingResult;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostPagingResponseDto {

    private List<PostResponseDto> posts;
    private PagingResult page;

    private PostPagingResponseDto(List<PostResponseDto> posts, PagingResult page) {
        this.posts = posts;
        this.page = page;
    }

    public static PostPagingResponseDto of(Page<PostResponseDto> posts, PagingData pagingData) {
        return new PostPagingResponseDto(posts.getContent(), PagingResult.of(posts, pagingData));
    }
}
