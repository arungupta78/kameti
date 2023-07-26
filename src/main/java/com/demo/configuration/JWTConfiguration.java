package com.demo.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("application.security.jwt")
@Data
public class JWTConfiguration {
    String secretKey;
    long expiration;
    RefreshToken refreshToken;
}
