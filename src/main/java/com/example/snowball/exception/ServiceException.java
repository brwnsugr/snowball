package com.example.snowball.exception;

import com.example.snowball.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

  protected HttpStatus status;
  protected String message = "";
  protected ErrorCode errorCode = ErrorCode.NOT_FOUND;

  public ServiceException(HttpStatus status, ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.status = status;
    this.message = errorCode.getDescription();
  }

  @Override
  public String getMessage() {
    return this.message;
  }


}
