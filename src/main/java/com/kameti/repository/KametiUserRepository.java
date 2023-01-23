package com.kameti.repository;

import com.kameti.model.KametiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KametiUserRepository extends JpaRepository<KametiUser, Long> {
    KametiUser findByEmail(String email);
}
