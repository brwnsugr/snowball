package com.example.snowball.web.dto;

import com.example.snowball.web.domain.posts.Posts;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostsListResponseDto {
  private Long id;
  private String title;
  private String author;
  private LocalDateTime modifiedDate;

  public PostsListResponseDto(Posts entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.author = entity.getAuthor();
    this.modifiedDate = entity.getModifiedDate();
  }
}
