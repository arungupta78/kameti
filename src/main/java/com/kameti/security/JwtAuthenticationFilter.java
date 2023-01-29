package com.kameti.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.kameti.model.KametiUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final String BEARER = "Bearer ";
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader(AUTHORIZATION);
    final String jwt;
    final String userId;
    if (authHeader == null || !authHeader.startsWith(BEARER)) {
      filterChain.doFilter(request, response);
      return;
    }

    jwt = authHeader.substring(BEARER.length());
    userId = jwtService.extractUserId(jwt);

    if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      KametiUser userDetails = (KametiUser) this.userDetailsService.loadUserByUsername(userId);
      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
