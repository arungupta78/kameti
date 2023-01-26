package com.kameti.service;

import com.kameti.model.KametiUser;
import java.util.List;

public interface UserService {
  KametiUser saveUser(KametiUser user);

  KametiUser fetchUser(String email);

  List<KametiUser> fetchUsers();
}
