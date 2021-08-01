package com.example.snowball.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {
  private String status;
  private T body;
}
