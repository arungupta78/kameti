package com.kameti.repository;

import com.kameti.model.KametiUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KametiUserRepository extends JpaRepository<KametiUser, Long> {
    Optional<KametiUser> findByEmail(String email);
}
