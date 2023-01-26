package com.kameti.controller;

import com.kameti.model.AuthenticationRequest;
import com.kameti.model.AuthenticationResponse;
import com.kameti.model.RegisterRequest;
import com.kameti.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest requestBody) {
    return ResponseEntity.ok(service.register(requestBody));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest requestBody) {
    return ResponseEntity.ok(service.authenticate(requestBody));
  }
}
