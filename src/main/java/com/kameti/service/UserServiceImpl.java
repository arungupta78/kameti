package com.kameti.service;

import com.kameti.model.KametiUser;
import com.kameti.model.Role;
import com.kameti.repository.KametiUserRepository;
import com.kameti.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
   private final KametiUserRepository userRepository;
    private final RoleRepo roleRepo;
    @Override
    public KametiUser saveUser(KametiUser user) {
        log.info("Saving new user to the database");
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to User {}", roleName, email);
        KametiUser user = userRepository.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public KametiUser fetchUser(String email) {
        log.info("Fetching User {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<KametiUser> fetchUsers() {
        log.info("Fetching all Users");
        return userRepository.findAll();
    }
}
