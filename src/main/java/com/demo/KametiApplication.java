package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KametiApplication {

  public static void main(String[] args) {
    var apc = SpringApplication.run(KametiApplication.class, args);
    for (String beanDefinitionName : apc.getBeanDefinitionNames()) {
      System.out.println(beanDefinitionName);
    }
  }
}
