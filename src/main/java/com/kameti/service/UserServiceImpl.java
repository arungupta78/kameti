package com.kameti.service;

import com.kameti.model.KametiUser;
import com.kameti.repository.KametiUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
  private final KametiUserRepository userRepository;

  @Override
  public KametiUser saveUser(KametiUser user) {
    log.info("Saving new user to the database");
    return userRepository.save(user);
  }

  @Override
  public KametiUser fetchUser(String email) {
    log.info("Fetching User {}", email);
    return userRepository.findByEmail(email).orElse(null);
  }

  @Override
  public List<KametiUser> fetchUsers() {
    log.info("Fetching all Users");
    return userRepository.findAll();
  }
}
