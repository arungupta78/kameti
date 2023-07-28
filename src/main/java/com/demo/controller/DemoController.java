package com.demo.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@SecurityRequirement(name = "bearerAuth")
public class DemoController {

  @GetMapping
  public ResponseEntity<String> demo() {
    return ResponseEntity.ok("hello from demo");
  }
}
