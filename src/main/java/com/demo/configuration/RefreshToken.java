package com.demo.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RefreshToken {
    private long expiration;
}
