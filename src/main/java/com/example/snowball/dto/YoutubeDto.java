package com.example.snowball.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class YoutubeDto {

  private String title; // 동영상 제목
  private String thumbnailPath; //동영상 썸네일 경로
  private String videoId; // 동영상 식별 ID

  @Builder(toBuilder = true)
  public YoutubeDto(String title, String thumbnailPath, String videoId) {
    this.title = title;
    this.thumbnailPath = thumbnailPath;
    this.videoId = videoId;
  }

}
