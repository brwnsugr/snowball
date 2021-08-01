package com.example.snowball.controller;

import com.example.snowball.service.PostsService;
import com.example.snowball.domain.posts.PostsRepository;
import com.example.snowball.dto.PostsListResponseDto;
import com.example.snowball.dto.PostsResponseDto;
import com.example.snowball.dto.PostsSaveRequestDto;
import com.example.snowball.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

  private final PostsService postsService;
  private final PostsRepository postsRepository;

  @PostMapping("/api/v1/posts")
  public Long save(@RequestBody PostsSaveRequestDto requestDto) {
    System.out.println("dddddddd");
    return 1L;
//    return postsService.save(requestDto);
  }

  @PutMapping("/api/v1/posts/{id}")
  public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
    return postsService.update(id, requestDto);
  }

  @DeleteMapping("/api/v1/posts/{id}")
  public Long delete(@PathVariable Long id) {
    postsService.delete(id);
    return id;
  }


  @GetMapping("/api/v1/posts/{id}")
  public PostsResponseDto findById(@PathVariable Long id) {
    return postsService.findById(id);
  }

  @GetMapping("/api/v1/posts/list")
  public List<PostsListResponseDto> findAll() {
    return postsService.findAllDesc();
  }
}
