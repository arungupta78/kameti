package com.kameti.repository;

import com.kameti.model.KametiUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KametiUserRepository extends JpaRepository<KametiUser, Long> {
  Optional<KametiUser> findByEmail(String email);
}
