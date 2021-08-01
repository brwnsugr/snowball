package com.example.snowball.service.accesstoken;

import com.example.snowball.config.ServerProperty;
import com.example.snowball.domain.accesstoken.AccessTokenType;
import com.example.snowball.dto.LoginTokenResDto;
import com.example.snowball.util.AES256Util;
import com.example.snowball.util.JwtTokenProvider;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableConfigurationProperties({ServerProperty.class})
@RequiredArgsConstructor
public class RegistAccessTokenService {

  @Resource
  ServerProperty serverProperty;

  private final JwtTokenProvider jwtTokenProvider;
  private final CoreService coreService;

  @Transactional
  public LoginTokenResDto regist(String email, String userId, String machineId, String sessionUuid)
    throws Exception{
    String encUserId = AES256Util.encrypt(userId, serverProperty.getCookieSecret());
    Map<String, Object> claims = new HashMap<>();
    claims.put(CoreService.EID, encUserId);
    claims.put(CoreService.MACHINE_ID, machineId);
    claims.put(CoreService.SID, sessionUuid);

    String token = jwtTokenProvider.createToken(email, claims);
    String refreshToken = jwtTokenProvider.createRefreshToken(email, claims);
    coreService.registerToken(Long.parseLong(userId), refreshToken, AccessTokenType.REFRESH);
    return coreService.buildLoginTokenResDto(token, refreshToken);
  }







}
