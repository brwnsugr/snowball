package com.example.snowball.exception;

import com.example.snowball.common.code.ErrorCode;

public class UserAuthException extends RuntimeException{
  private ErrorCode errorCode;

  public UserAuthException(ErrorCode errorCode) {
    super(errorCode.getDescription());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return this.errorCode;
  }

}
