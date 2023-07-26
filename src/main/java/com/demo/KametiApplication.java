package com.demo;

import com.demo.configuration.JWTConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JWTConfiguration.class)
public class KametiApplication {

  public static void main(String[] args) {
    SpringApplication.run(KametiApplication.class, args);
  }
}
