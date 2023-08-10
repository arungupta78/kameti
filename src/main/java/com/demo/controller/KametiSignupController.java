package com.demo.controller;

import com.demo.model.KametiSignupRequest;
import com.demo.model.KametiSuccessResponse;
import com.demo.service.KametiSignupService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class KametiSignupController {
  @Autowired KametiSignupService service;

  @PostMapping
  public ResponseEntity<KametiSuccessResponse> saveKameti(
      @Parameter(description = "Request Body for kameti signup") @RequestBody
          KametiSignupRequest requestBody) {
    return ResponseEntity.ok().body(service.signUpKameti(requestBody));
  }
}
