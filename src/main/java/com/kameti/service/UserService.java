package com.kameti.service;

import com.kameti.model.KametiUser;
import com.kameti.model.Role;

import java.util.List;

public interface UserService {
    KametiUser saveUser(KametiUser user);

    Role saveRole(Role role);

    void addRoleToUser(String email, String roleName);

    KametiUser fetchUser(String email);

    List<KametiUser> fetchUsers();

}
