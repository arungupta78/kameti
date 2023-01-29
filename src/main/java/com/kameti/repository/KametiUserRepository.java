package com.kameti.repository;

import com.kameti.exception.KametiUserAlreadyExistsException;
import com.kameti.model.KametiUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KametiUserRepository extends JpaRepository<KametiUser, Long> {
  Optional<KametiUser> findByEmail(String email);

  default KametiUser registerNewUserAccount(KametiUser user) {
    if (existsByEmail(user.getEmail())) {
      throw new KametiUserAlreadyExistsException(
          "An account already exists with email address: " + user.getEmail());
    } else {
      return save(user);
    }
  }

  boolean existsByEmail(String email);

  Optional<KametiUser> findById(String id);
}
