package com.example.snowball.web;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void hello_가_리턴된다() throws Exception {
    String hello = "hello";

    mvc.perform(get("/hello"))
        .andExpect(status().isOk())
        .andExpect(content().string(hello));
  }

  @Test
  public void helloDto가_리턴된다() throws Exception {
    String name = "hello";
    int amount = 1000;

    mvc.perform(get("/hello/dto")
        .param("name", name)
        .param("amount", String.valueOf(amount))
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(name)))
        .andExpect(jsonPath("$.amount", is(amount)));



  }

}
