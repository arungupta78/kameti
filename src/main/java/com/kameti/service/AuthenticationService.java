package com.kameti.service;

import com.kameti.model.*;
import com.kameti.repository.KametiUserRepository;
import com.kameti.security.JwtService;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
            .creationDate(Date.from(Instant.now()))
            .role(Role.USER)
            .build();
    return Optional.of(repository.registerNewUserAccount(user))
        .map(jwtService::generateToken)
        .map(AuthenticationResponse::new)
        .orElseThrow();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest requestBody) {
    return repository
        .findByEmail(requestBody.getEmail())
        .map(
            user ->
                new UsernamePasswordAuthenticationToken(user.getId(), requestBody.getPassword()))
        .map(authenticationManager::authenticate)
        .map(Authentication::getPrincipal)
        .map(KametiUser.class::cast)
        .map(jwtService::generateToken)
        .map(AuthenticationResponse::new)
        .orElseThrow();
  }
}
