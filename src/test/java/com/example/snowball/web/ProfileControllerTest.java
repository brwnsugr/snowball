package com.example.snowball.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProfileControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

//  @Test
//  public void profile은_인증없이_호출된다() throws Exception{
//    String expected = "default";
//
//    ResponseEntity<String> response = restTemplate.getForEntity("/profile", String.class);
//
//    Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
//    Assertions.assertEquals(response.getBody(), expected);
//  }

}
