package com.chinhae.newsfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NbcW6NewsfeedApplication {

  public static void main(String[] args) {
    SpringApplication.run(NbcW6NewsfeedApplication.class, args);
  }

}
