package com.example.snowball.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void 메인페이지_로딩() {
    //when
    String body = this.restTemplate.getForObject("/", String.class);

    //then
    assertThat(body).contains("snowball");
  }

}
