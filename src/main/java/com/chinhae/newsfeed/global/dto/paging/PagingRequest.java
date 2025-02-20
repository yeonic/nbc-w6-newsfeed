package com.chinhae.newsfeed.global.dto.paging;

import com.chinhae.newsfeed.global.messages.ValidationConst;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort.Direction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PagingRequest {

    @Min(value = 0, message = ValidationConst.INVALID_PAGENUM)
    private int pageNum = 1;
    private int pageSize = 10;
    private OrderBy orderBy = OrderBy.CREATED_AT;
    private Direction orderDirection = Direction.DESC;

    @Builder
    public PagingRequest(int pageNum, int pageSize, String orderBy, String orderDirection) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;

        // enum OrderBy의 string 값만 허용
        this.orderBy = OrderBy.ofString(orderBy);

        // "ASC"(오름차순) 혹은 "DESC"(내림차순)만 허용
        this.orderDirection = Direction.fromString(orderDirection);
    }
}
