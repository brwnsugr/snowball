package com.example.snowball.controller;

import com.example.snowball.config.auth.LoginUser;
import com.example.snowball.service.YoutubeService;


import com.example.snowball.domain.user.Role;
import com.example.snowball.dto.YoutubeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class YoutubeController {

  private final YoutubeService youtubeService;

//  @Autowired
//  public YoutubeController(final YoutubeProvider youtubeProvider) {
//    this.youtubeProvider = youtubeProvider;
//  }

  @LoginUser(isAuth = Role.USER)
  @GetMapping("/api/v1/youtube")
  public YoutubeDto Index() {
    return youtubeService.get();
  }


  @GetMapping("/api/v1/youtube-channel")
  public void test() {
    youtubeService.getAllVideosFromChannel();
  }

  @GetMapping("/api/v1/youtube-channel-section")
  public void test2() {
    youtubeService.channelSection();
  }

}
