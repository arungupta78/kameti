package com.demo.service;

import com.demo.model.*;
import com.demo.repository.UserRepository;
import com.demo.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest requestBody) {
    return Optional.of(repository.registerNewUserAccount(buildUser(requestBody)))
        .map(jwtService::generateAccessAndRefreshToken)
        .map(buildAuthenticationResponse())
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
        .map(DemoUser.class::cast)
        .map(jwtService::generateAccessAndRefreshToken)
        .map(buildAuthenticationResponse())
        .orElseThrow();
  }

  private DemoUser buildUser(RegisterRequest requestBody) {
    return DemoUser.builder()
        .firstname(requestBody.getFirstname())
        .lastname(requestBody.getLastname())
        .email(requestBody.getEmail())
        .password(passwordEncoder.encode(requestBody.getPassword()))
        .creationDate(Date.from(Instant.now()))
        .role(Role.USER)
        .build();
  }

  private Function<JwtService.Token, AuthenticationResponse> buildAuthenticationResponse() {
    return token ->
        AuthenticationResponse.builder()
            .accessToken(token.accessToken())
            .refreshToken(token.refreshToken())
            .build();
  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    final String authHeader = request.getHeader(AUTHORIZATION);
    final String refreshToken;
    final String userId;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    refreshToken = authHeader.substring("Bearer ".length());
    userId = jwtService.extractUserId(refreshToken);

    if (userId != null) {
      DemoUser userDetails = repository.findById(userId).orElseThrow();
      if (jwtService.isTokenValid(refreshToken, userDetails)) {
        var accessToken = jwtService.generateAccessToken(userDetails);
        var authResponse =
            AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
