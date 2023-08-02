package com.demo.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseActuator implements HealthIndicator {

  public static final String DB_SERVICE = "Database Service";

  boolean isDBHealthGood() {
    return true;
  }

  @Override
  public Health health() {
    if (isDBHealthGood()) {
      return Health.up().withDetail(DB_SERVICE, "Database service is running").build();
    } else {
      return Health.down().withDetail(DB_SERVICE, "Database service is DOWN").build();
    }
  }
}
