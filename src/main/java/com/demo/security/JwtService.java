package com.demo.security;

import com.demo.configuration.JWTConfiguration;
import com.demo.model.DemoUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  @Autowired private JWTConfiguration jwtConfiguration;

  public String extractUserId(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public Token generateAccessAndRefreshToken(DemoUser userDetails) {
    String accessToken = generateAccessToken(userDetails);
    String refreshToken = generateRefreshToken(userDetails);
    return new Token(accessToken, refreshToken);
  }

  public String generateAccessToken(DemoUser userDetails) {
    return buildToken(Collections.emptyMap(), userDetails, jwtConfiguration.getExpiration());
  }

  public String generateRefreshToken(DemoUser userDetails) {
    return buildToken(
        Collections.emptyMap(), userDetails, jwtConfiguration.getRefreshToken().getExpiration());
  }

  private String buildToken(
      Map<String, Object> extraClaims, DemoUser userDetails, long expiration) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getId().toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, DemoUser userDetails) {
    final String username = extractUserId(token);
    return username.equals(userDetails.getId().toString())
        && !isTokenExpired(token)
        && userDetails.isAccountNonExpired()
        && userDetails.isAccountNonLocked()
        && userDetails.isCredentialsNonExpired()
        && userDetails.isEnabled();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtConfiguration.getSecretKey());
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public record Token(String accessToken, String refreshToken) {}
}
