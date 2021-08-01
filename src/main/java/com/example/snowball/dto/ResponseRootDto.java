package com.example.snowball.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ResponseRootDto<T> {

  @ApiModelProperty(value = "결과 상태", allowableValues = "200 OK")
  private String status;

  private Body body;
  public ResponseRootDto(String status, boolean error, String errorCode, String errorMessage, T result)
  {
    this.status = status;
    this.body = Body.builder().error(error).errorCode(errorCode).errorMessage(errorMessage).result(result).build();
  }

  @Data
  @Builder
  @NoArgsConstructor
  public static class Body<T> {
    private T result;
    @ApiModelProperty(value = "오류 메시지", allowableValues = "")
    private String errorMessage;
    @ApiModelProperty(value = "오류코드", allowableValues = "")
    private String errorCode;
    @ApiModelProperty(value = "오류여부", allowableValues = "false")
    private boolean error;

    public Body(T result, String errorMessage, String errorCode, boolean error)
    {
      this.result = result;
      this.error = error;
      this.errorCode = errorCode;
      this.errorMessage = errorMessage;
    }
  }
}
