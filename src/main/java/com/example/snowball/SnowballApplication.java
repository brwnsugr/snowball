package com.example.snowball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication
public class SnowballApplication {

  public static void main(String[] args) {
    SpringApplication.run(SnowballApplication.class, args);
  }

}
