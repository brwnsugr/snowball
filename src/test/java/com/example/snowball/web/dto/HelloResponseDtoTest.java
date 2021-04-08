package com.example.snowball.web.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class HelloResponseDtoTest {

  @Test
  public void 롬복_기능_테스트() {
    //given
    String name = "test";
    int amount = 1000;

    //when
    HelloResponseDto dto = new HelloResponseDto(name, amount);

    //then
    Assertions.assertEquals(dto.getName(), name);
    Assertions.assertEquals(dto.getAmount(), amount);
  }

}
