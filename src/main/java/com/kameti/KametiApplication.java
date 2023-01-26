package com.kameti;

import com.kameti.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KametiApplication {

  public static void main(String[] args) {
    SpringApplication.run(KametiApplication.class, args);
  }

  @Bean
  CommandLineRunner run(UserService userService) {
    return args -> {};
  }
}
