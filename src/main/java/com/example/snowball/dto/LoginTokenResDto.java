package com.example.snowball.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginTokenResDto {

  private final String tokenType = "bearer";

  private String accessToken;

  private long expiresIn;

  private String refreshToken;

  private long refreshTokenExpiresIn;

  @Builder
  public LoginTokenResDto(String accessToken, String refreshToken, long expiresIn, long refreshTokenExpiresIn) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.expiresIn = expiresIn;
    this.refreshTokenExpiresIn = refreshTokenExpiresIn;
  }

}
