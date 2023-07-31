package com.demo.service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.demo.annotation.LogSignIn;
import com.demo.model.*;
import com.demo.repository.TokenRepository;
import com.demo.repository.UserRepository;
import com.demo.security.JwtService;
import com.demo.security.JwtService.TokenRecord;
import com.demo.token.Token;
import com.demo.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest requestBody) {
    DemoUser user = repository.registerNewUserAccount(buildUser(requestBody));
    return Optional.of(user)
        .map(jwtService::generateAccessAndRefreshToken)
        .map(tokenRecord -> saveToken(user, tokenRecord, false))
        .map(buildAuthenticationResponse())
        .orElseThrow();
  }

  @LogSignIn
  public AuthenticationResponse authenticate(AuthenticationRequest requestBody) {
    Optional<DemoUser> userOptional = repository.findByEmail(requestBody.getEmail());
    return userOptional
        .map(
            user ->
                new UsernamePasswordAuthenticationToken(user.getId(), requestBody.getPassword()))
        .map(authenticationManager::authenticate)
        .map(Authentication::getPrincipal)
        .map(DemoUser.class::cast)
        .map(jwtService::generateAccessAndRefreshToken)
        .map(tokenRecord -> saveToken(userOptional.orElseThrow(), tokenRecord, true))
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

  private Function<TokenRecord, AuthenticationResponse> buildAuthenticationResponse() {
    return tokenRecord ->
        AuthenticationResponse.builder()
            .accessToken(tokenRecord.accessToken())
            .refreshToken(tokenRecord.refreshToken())
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

  private TokenRecord saveToken(DemoUser user, TokenRecord tokenRecord, boolean existingUser) {
    if (existingUser) {
      revokeOldTokens(user);
    }
    var token =
        Token.builder()
            .user(user)
            .token(tokenRecord.accessToken())
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
    return tokenRecord;
  }

  private void revokeOldTokens(DemoUser user) {
    var existingTokens = tokenRepository.findAllValidTokensByUser(user.getId());
    if (existingTokens.isEmpty()) {
      return;
    }
    existingTokens.forEach(token -> token.setRevoked(true));
    tokenRepository.saveAll(existingTokens);
  }
}
