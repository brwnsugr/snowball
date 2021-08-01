package com.example.snowball.dto;

import javax.validation.constraints.NotBlank;
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
public class LoginDto {

  @NotBlank(message = "로그인 아이디를 입력하세요.")
  private String login;

  @NotBlank(message = "패스워드를 입력하세요.")
  @Size(min=6, max = 20, message = "6자 이상, 20자 이하로 입력하세요.")
  private String password;
}
