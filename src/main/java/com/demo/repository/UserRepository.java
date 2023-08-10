package com.demo.repository;

import com.demo.entity.DemoUser;
import com.demo.exception.UserAlreadyExistsException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DemoUser, Long> {
  Optional<DemoUser> findByEmail(String email);

  default DemoUser registerNewUserAccount(DemoUser user) {
    if (existsByEmail(user.getEmail())) {
      throw new UserAlreadyExistsException(
          "An account already exists with email address: " + user.getEmail());
    } else {
      return save(user);
    }
  }

  boolean existsByEmail(String email);

  Optional<DemoUser> findById(String id);
}
