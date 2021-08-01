package com.example.snowball.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ValidErrorResultDto {

  private String field;
  private String defaultMessage;
  private String rejectedValue;
  public ValidErrorResultDto(String field, String defaultMessage, String rejectedValue) {
    this.field = field;
    this.defaultMessage = defaultMessage;
    this.rejectedValue = rejectedValue;
  }
}
