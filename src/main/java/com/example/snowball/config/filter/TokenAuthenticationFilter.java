package com.example.snowball.config.filter;

import com.example.snowball.common.code.ErrorCode;
import com.example.snowball.domain.user.User;
import com.example.snowball.domain.user.UserRepository;
import com.example.snowball.exception.ServiceException;
import com.example.snowball.service.UserService;
import com.example.snowball.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenAuthenticationFilter extends OncePerRequestFilter {


  private static final String[] PUBLIC_URI = {
     "/", "/swagger-ui.html","/api/v1/user/signup", "/api/v1/helo", "api/v1/user/login", "/webjars/springfox-swagger-ui/springfox.css"
  };
//  private final UserService userService;
  @Autowired JwtTokenProvider jwtTokenProvider;
  @Autowired
  UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
//    String parsedOriginalToken = jwtTokenProvider.getOriginalToken(request);
    String parsedToken2 = jwtTokenProvider.parseTokenString(request);
//    Claims claims = jwtTokenProvider.decodeRefreshToken(parsedToken2);
    Claims claims2 = jwtTokenProvider.decodeToken(parsedToken2);
    String loginId = (String) claims2.get("sub");
    if(jwtTokenProvider.validate(parsedToken2)) {
      User user = userRepository.findByLogin(loginId);
      if(user == null) {
        throw new ServiceException(HttpStatus.BAD_REQUEST, ErrorCode.NO_EXIST_LOGIN_ID);
      }

    }
    else {
      throw new ServletException("유효하지않은 인증토큰 입니다. 인증토큰 회원 정보 오류");
    }
    System.out.println("after got filtered!");
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    for(int i = 0; i < PUBLIC_URI.length; i++) {
      if (path.contains(PUBLIC_URI[i])) return true;
    }
    return false;
  }
}
