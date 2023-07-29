package com.demo.service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.demo.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public record LogoutService(TokenRepository tokenRepository) implements LogoutHandler {
  @Override
  public void logout(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    final String authHeader = request.getHeader(AUTHORIZATION);
    final String jwt;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    jwt = authHeader.substring(7);
    var storedToken = tokenRepository.findByToken(jwt);

    storedToken.ifPresent(
        token -> {
          token.setRevoked(true);
          token.setRevoked(true);
          tokenRepository.save(token);
        });
  }
}
