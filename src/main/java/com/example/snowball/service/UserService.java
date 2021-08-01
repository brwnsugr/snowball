package com.example.snowball.service;

import com.example.snowball.common.code.ErrorCode;
import com.example.snowball.domain.user.Role;
import com.example.snowball.domain.user.User;
import com.example.snowball.domain.user.UserRepository;
import com.example.snowball.dto.LoginTokenResDto;
import com.example.snowball.dto.SignupDto;
import com.example.snowball.exception.ServiceException;
import com.example.snowball.serializer.UserSessionSerializer;
import com.example.snowball.service.accesstoken.RegistAccessTokenService;
import com.example.snowball.util.NetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends BaseApiService {

  private final UserRepository userRepository;
  private final RegistAccessTokenService registAccessTokenService;

  public HashMap<String, Object> signup(
      @Valid SignupDto signupDto, HttpServletRequest request, HttpServletResponse response) {

    User signUpUser = userRepository.findByLogin(signupDto.getLogin());

    if(signUpUser != null) {
      return makeErrorResult(ErrorCode.EXIST_LOGIN_ID);
    }

    signUpUser = signupDto.toEntity();

//    RandomStringUtils
    String salt = RandomStringUtils.randomNumeric(16);
    signUpUser.setSalt(salt);
    signUpUser.setHashedPassword(makeHashedPassword(signupDto.getPassword(), salt));
    signUpUser.setRole(Role.USER);
    signUpUser.setName(" ");

    String clientIp = NetUtil.getRemoteIp(request);
    updateAccessLog(signUpUser, clientIp, true);

    User user = userRepository.save(signUpUser);

    return makeResult(makeUserSessionResult(user));
  }

  public LoginTokenResDto createTokenWithLogin(String login, String password,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map<String, Object> result = this.login(login, password, request, response);
    Map<String, Object> users = (Map<String, Object>)result.get("result");
    return registAccessTokenService.regist(
        login,
        users.get("id").toString(),
        request.getHeader("machine-id"),
        (String) request.getAttribute("sid"));
  }

  public Map<String, Object> login(
      String login, String password, HttpServletRequest request, HttpServletResponse response
  ) throws ServiceException {
    User user = userRepository.findByLogin(login);

    if(user == null) {
      throw new ServiceException(HttpStatus.FORBIDDEN, ErrorCode.NO_EXIST_LOGIN_ID);
    }

    if(!authenticate(user.getHashedPassword(), password, user.getSalt())) {
      throw new ServiceException(HttpStatus.FORBIDDEN, ErrorCode.INVALID_PASSWORD);
    }
    String clientIp = NetUtil.getRemoteIp(request);
    updateAccessLog(user, clientIp, true);
    userRepository.save(user);
    //Todo: cookie, 세션 설정

    //setcookie, createLoginSession

    return makeResult(makeUserSessionResult(user));
  }

  public Map<String, Object> makeUserSessionResult(User user) {
    if(user == null) {
      return getDefaultUserSessionResult();
    }
    try{
      Map<String, Object> resultMap = applySerializer(user, User.class, new UserSessionSerializer());
      return resultMap;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Map<String, Object> getDefaultUserSessionResult() {
    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("id", null);
    resultMap.put("name", null);
    resultMap.put("login", null);
    return resultMap;
  }

  public boolean authenticate(String hashedPassword, String password, String salt) {
    String plainText = new StringBuilder().append(password).append("wibble").append(salt).toString();
    String encryptText = DigestUtils.sha256Hex(plainText);
    return hashedPassword.equals(encryptText);
  }



  private String makeHashedPassword(String password, String salt) {
    String plainPassword = new StringBuilder().append(password).append("wibble").append(salt).toString();
    return DigestUtils.sha256Hex(plainPassword);
  }

  private void updateAccessLog(User user, String clientIp, boolean isLogin) {
    LocalDateTime now = LocalDateTime.now();
    if(isLogin) {
      user.setLastLoggedIn(now);
    }
    if(user.getLastLoggedIp() == null || !user.getLastLoggedIp().equals(clientIp)) {
      user.setLastLoggedIp(clientIp);
    }
    // 1분마다 갱신
    if(user.getLastSeenAt() == null) {
      user.setLastSeenAt(now);
    } else {
      Long diffSecond = ChronoUnit.SECONDS.between(user.getLastSeenAt(), now);
      if (diffSecond > 60) {
        user.setLastSeenAt(now);
      }
    }
  }

  private void setCookie(User user, HttpServletResponse response) {
    Long userId = user.getId();
    String uuid = String.format("%s%s%s", "%22", user.getUuid(), "%22");

  }


  public User getUserByEmail(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    return user;
  }

}
