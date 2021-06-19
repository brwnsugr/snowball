package com.example.snowball.service;

import com.example.snowball.web.domain.user.User;
import com.example.snowball.web.domain.user.UserRepository;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;



  public User getUserByEmail(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    return user;
  }

}
