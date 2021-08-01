package com.example.snowball.web;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.snowball.domain.posts.Posts;
import com.example.snowball.domain.posts.PostsRepository;
import com.example.snowball.dto.PostsSaveRequestDto;
import com.example.snowball.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class PostsApiControllerTest {

//  @LocalServerPort
//  private int port;
//
//  @Autowired
//  private TestRestTemplate restTemplate;
//
//  @Autowired
//  private PostsRepository postsRepository;
//
//  @Autowired
//  private WebApplicationContext context;
//
//  private MockMvc mvc;
//
//  @BeforeEach
//  public void setup() {
//    mvc = MockMvcBuilders
//        .webAppContextSetup(context)
//        .apply(springSecurity())
//        .build();
//  }
//
//  @After
//  public void tearDown() throws Exception {
//    postsRepository.deleteAll();
//  }
//
//  @Test
//  @WithMockUser(roles = "USER")
//  public void Posts_등록된다() throws Exception {
//    //given
//    String title = "title";
//    String content = "content";
//    PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
//        .title(title)
//        .content(content)
//        .author("author")
//        .build();
//
//    String url = "http://localhost:" + port + "/api/v1/posts";
//
//    //when
//    mvc.perform(
//        post(url)
//            .contentType(MediaType.APPLICATION_JSON_VALUE)
//            .content(new ObjectMapper().writeValueAsString(requestDto)))
//        .andExpect(status().isOk());
//
//    //then
////    List<Posts> all = postsRepository.findAll();
////    Assertions.assertEquals(all.get(0).getTitle(), title);
////    Assertions.assertEquals(all.get(0).getContent(), content);
//  }
//
//  @Test
//  @WithMockUser(roles = "USER")
//  public void Posts_수정된다() throws Exception {
//    //given
//    Posts savedPosts = postsRepository.save(Posts.builder()
//    .title("title")
//    .content("content")
//    .author("author").build());
//
//    Long updateId = savedPosts.getId();
//    String expectedTitle = "title2";
//    String expectedContent = "content2";
//
//    PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
//        .title(expectedTitle)
//        .content(expectedContent)
//        .build();
//
//    String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
//
//    //when
//    mvc.perform(
//        put(url)
//            .contentType(MediaType.APPLICATION_JSON_VALUE)
//            .content(new ObjectMapper().writeValueAsString(requestDto)))
//        .andExpect(status().isOk());
//    //then
//    List<Posts> all = postsRepository.findAll();
//    Assertions.assertEquals(all.get(0).getTitle(), expectedTitle);
//    Assertions.assertEquals(all.get(0).getContent(), expectedContent);
//  }

}
