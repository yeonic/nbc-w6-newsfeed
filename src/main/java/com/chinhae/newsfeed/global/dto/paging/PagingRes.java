package com.chinhae.newsfeed.global.dto.paging;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PagingRes {
  private int pageNo;
  private int pageSize;
  private String sortBy;
  private long totalElement;
  private int totalPage;

  @Builder
  public PagingRes(int pageNo, int pageSize, String sortBy, long totalElement, int totalPage) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
    this.sortBy = sortBy;
    this.totalElement = totalElement;
    this.totalPage = totalPage;
  }
}
