package com.example.snowball.dto;

import com.example.snowball.domain.user.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {

  @NotBlank(message = "아이디를 입력하세요.")
  private String login;

  @NotBlank(message = "이메일을 입력하세요.")
  @Pattern(
      regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,11}$",
      message = "이메일 형식에 맞게 입력하세요."
  )
  private String email;

  @NotBlank(message = "패스워드를 입력하세요.")
  @Size(min = 6, max = 20, message = "6자 이상, 20자 이하로 입력하세요.")
  private String password;

  public User toEntity() {
    return User.builder()
        .login(login)
        .email(email)
        .build();
  }
}
