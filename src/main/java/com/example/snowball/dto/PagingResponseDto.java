package com.example.snowball.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class PagingResponseDto<T> implements Serializable {
  private Long total;
  private int totalPages;
  private int pageSize;
  private boolean hasPrevious;
  private boolean hasNext;
  private int page;
  private List<T> contents;

  public PagingResponseDto(Pageable pageable) {
    this.total = 0L;
    this.totalPages = 0;
    this.pageSize = pageable.getPageSize();
    this.hasPrevious = false;
    this.hasNext = false;
    this.page = pageable.getPageNumber() + 1;
    this.contents = new ArrayList<>();
  }

}
