package com.example.snowball.interceptor;

import com.example.snowball.common.code.ErrorCode;
import com.example.snowball.config.auth.LoginUser;
import com.example.snowball.config.auth.dto.SessionUser;
import com.example.snowball.exception.UserAuthException;
import com.example.snowball.service.UserService;
import com.example.snowball.web.domain.user.Role;
import java.lang.annotation.Annotation;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthInterceptor implements HandlerInterceptor {

  private final UserService userService;
  private final HttpSession httpSession;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    LoginUser loginUser = getAnnotation(handlerMethod, LoginUser.class);

    if (loginUser == null) return true; // @LoginUser 어노테이션이 없으면, 권한 Scope 이 필요 없으므로, 항상 true 로 패스.

    SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
    if(sessionUser == null && loginUser.isAuth() == Role.USER) {
      throw new UserAuthException(ErrorCode.NOT_LOGED_IN);
    }
    return true;
  }


  private <A extends Annotation> A getAnnotation(
      HandlerMethod handlerMethod, Class<A> annotationType) {
    return Optional.ofNullable(handlerMethod.getMethodAnnotation(annotationType))
        .orElse(handlerMethod.getBeanType().getAnnotation(annotationType));
  }

}
