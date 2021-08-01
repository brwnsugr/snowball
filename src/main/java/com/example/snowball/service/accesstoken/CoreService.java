package com.example.snowball.service.accesstoken;

import com.example.snowball.domain.accesstoken.AccessToken;
import com.example.snowball.domain.accesstoken.AccessTokenRepository;
import com.example.snowball.domain.accesstoken.AccessTokenType;
import com.example.snowball.dto.LoginTokenResDto;
import com.example.snowball.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CoreService {

  public static final String MACHINE_ID = "machine_id";
  public static final String EID = "eid";
  public static final String SID = "sid";

  private final AccessTokenRepository accessTokenRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional
  public AccessToken registerToken(long userId, String token, AccessTokenType accessTokenType) {
    AccessToken accessToken = accessTokenRepository
        .findTopByUserIdAndIsActive(userId, true);
    if(accessToken == null) {
      accessToken = AccessToken.builder()
          .userId(userId)
          .token(token)
          .isActive(true)
          .type(accessTokenType)
          .build();
    } else {
      accessToken.setToken(token);
    }
    return accessTokenRepository.save(accessToken);
  }

  public LoginTokenResDto buildLoginTokenResDto(String token, String refreshToken) {
    Date now = new Date();
    return LoginTokenResDto.builder()
        .accessToken(token)
        .refreshToken(refreshToken)
        .expiresIn(
            (jwtTokenProvider.decodeToken(token).getExpiration().getTime() - now.getTime()) / 1000)
        .refreshTokenExpiresIn(
            (jwtTokenProvider.decodeRefreshToken(refreshToken).getExpiration().getTime() - now.getTime())/1000)
        .build();
  }

}
