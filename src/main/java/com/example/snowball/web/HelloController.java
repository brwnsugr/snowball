package com.example.snowball.web;

import com.example.snowball.service.YoutubeService;
import com.example.snowball.web.dto.HelloResponseDto;
import com.example.snowball.web.dto.YoutubeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {

  private final YoutubeService youtubeService;

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }

  @GetMapping("/hello/dto")
  public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
    return new HelloResponseDto(name, amount);
  }

  @GetMapping("/hello-youtube")
  public String youtubeHello() {
    youtubeService.get().toString();
  }



}
