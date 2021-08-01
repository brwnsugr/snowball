package com.example.snowball.controller;

import com.example.snowball.service.YoutubeService;
import com.example.snowball.dto.HelloResponseDto;
import com.example.snowball.dto.YoutubeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {

  private final YoutubeService youtubeService;

  @GetMapping("/api/v1/hello")
  public String hello() {
    return "hello server! V.1.1.1 (2021.08.01)" ;
  }

  @GetMapping("/api/v1/hello-auth")
  public String helloAuth() {
    return "you are authenticated!";
  }

  @GetMapping("/hello/dto")
  public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
    return new HelloResponseDto(name, amount);
  }

  @GetMapping("/hello-youtube")
  public String youtubeHello() {
    YoutubeDto youtubeDto = youtubeService.get();
    String title = youtubeDto.getTitle();
    return title;
  }



}
