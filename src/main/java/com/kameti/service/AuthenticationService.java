package com.kameti.service;

import com.kameti.model.*;
import com.kameti.repository.KametiUserRepository;
import com.kameti.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final KametiUserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest requestBody) {
    var user =
        KametiUser.builder()
            .firstname(requestBody.getFirstname())
            .lastname(requestBody.getLastname())
            .email(requestBody.getEmail())
            .password(passwordEncoder.encode(requestBody.getPassword()))
            .role(Role.USER)
            .build();
    repository.save(user);
    return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest requestBody) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(requestBody.getEmail(), requestBody.getPassword()));

    return repository
        .findByEmail(requestBody.getEmail())
        .map(jwtService::generateToken)
        .map(AuthenticationResponse::new)
        .orElseThrow();
  }
}
