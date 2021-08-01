package com.example.snowball.controller;

import com.example.snowball.dto.ResponseDto;
import java.util.Map;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/*")
public class BaseApiController {

  public static final String CURRENT_USER = "currentUser";
  public static ResponseEntity<ResponseDto> makeResponse(HttpStatus httpStatus,
      Map<String, Object> result) {
    return makeResponse(httpStatus, result);
  }

  public static ResponseEntity<ResponseDto> makeResponse(HttpStatus httpStatus, Map<String, Object> result, boolean isNoStore) {
    ResponseDto responseDto = ResponseDto.builder().status(httpStatus.toString())
        .body(result)
        .build();

    ResponseEntity.BodyBuilder body = ResponseEntity.status(httpStatus);
    if(isNoStore) {
      body.cacheControl(CacheControl.noStore());
    }
    return body.body(responseDto);
  }
}
