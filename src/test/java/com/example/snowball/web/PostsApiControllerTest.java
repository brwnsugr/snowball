package com.example.snowball.web;

import com.example.snowball.web.domain.posts.Posts;
import com.example.snowball.web.domain.posts.PostsRepository;
import com.example.snowball.web.dto.PostsSaveRequestDto;
import com.example.snowball.web.dto.PostsUpdateRequestDto;
import java.util.List;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PostsRepository postsRepository;

  @After
  public void tearDown() throws Exception {
    postsRepository.deleteAll();
  }

  @Test
  public void Posts_등록된다() throws Exception {
    //given
    String title = "title";
    String content = "content";
    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
        .title(title)
        .content(content)
        .author("author")
        .build();

    String url = "http://localhost:" + port + "/api/v1/posts";

    //when
    ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

    //then
    Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    List<Posts> all = postsRepository.findAll();
    Assertions.assertEquals(all.get(0).getTitle(), title);
    Assertions.assertEquals(all.get(0).getContent(), content);
  }

  @Test
  public void Posts_수정된다() throws Exception {
    //given
    Posts savedPosts = postsRepository.save(Posts.builder()
    .title("title")
    .content("content")
    .author("author").build());

    Long updateId = savedPosts.getId();
    String expectedTitle = "title2";
    String expectedContent = "content2";

    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
        .title(expectedTitle)
        .content(expectedContent)
        .build();

    String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

    HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

    //when
    ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

    //then
    Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    List<Posts> all = postsRepository.findAll();
    Assertions.assertEquals(all.get(0).getTitle(), expectedTitle);
    Assertions.assertEquals(all.get(0).getContent(), expectedContent);
  }



}
