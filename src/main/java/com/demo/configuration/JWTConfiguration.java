package com.demo.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.security.jwt")
@Data
public class JWTConfiguration {
  String secretKey;
  long expiration;
  RefreshToken refreshToken;
}
