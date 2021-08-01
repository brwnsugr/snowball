package com.example.snowball.controller;

import com.example.snowball.dto.LoginDto;
import com.example.snowball.dto.LoginTokenResDto;
import com.example.snowball.dto.ResponseDto;
import com.example.snowball.dto.ResponseRootDto;
import com.example.snowball.dto.SignupDto;
import com.example.snowball.service.BaseApiService;
import com.example.snowball.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "User",
    tags = {"User"})
@RequiredArgsConstructor
public class UserController extends BaseApiController {

  @Autowired UserService userService;

  @PostMapping("/user/signup")
  public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid SignupDto signupDto,
      HttpServletRequest request,
      HttpServletResponse response) {

    HashMap<String, Object> result = userService.signup(signupDto, request, response);
    if ((boolean) result.get("error")) {
      ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.FORBIDDEN.toString()).body(result).build();
      return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
    }

    ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.CREATED.toString()).body(result).build();
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  @ApiOperation(value = "로그인 with 토큰 발행")
  @PostMapping("/user/login")
  public ResponseEntity<ResponseRootDto<LoginTokenResDto>> loginWithCreateToken(
      @RequestHeader(name = "machine-id") String machineId,
      @RequestBody @Valid LoginDto loginDto,
      HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    LoginTokenResDto tokenWithLogin = userService
        .createTokenWithLogin(loginDto.getLogin(), loginDto.getPassword(),
            request, response);

    ResponseEntity<ResponseRootDto<LoginTokenResDto>> response1 = userService
        .response(HttpStatus.OK, tokenWithLogin);
    return response1;
  }





}
