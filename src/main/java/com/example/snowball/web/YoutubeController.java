package com.example.snowball.web;

import com.example.snowball.service.YoutubeService;


import com.example.snowball.service.YoutubeService;
import com.example.snowball.web.dto.YoutubeDto;
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

  @GetMapping("youtube")
  public YoutubeDto Index() {
    return youtubeService.get();
  }

}
