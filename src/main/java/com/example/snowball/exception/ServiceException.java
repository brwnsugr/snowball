package com.example.snowball.exception;

import com.example.snowball.common.code.ErrorCode;
import com.example.snowball.dto.ValidErrorResultDto;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

  protected List<ValidErrorResultDto> result;
  protected HttpStatus status;
  protected String message = "";
  protected ErrorCode errorCode = ErrorCode.NOT_FOUND;

  public ServiceException(HttpStatus status, ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.status = status;
    this.message = errorCode.getDescription();
  }

  public ServiceException(HttpStatus status, List<ValidErrorResultDto> result, String message) {
    this.result = result;
    this.status = status;
    this.message = message;
  }



  @Override
  public String getMessage() {
    return this.message;
  }


}
