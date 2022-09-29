package org.casbin.service;

import org.casbin.model.User;

import java.util.Optional;

public interface IUserService {
  Optional<User> login(String sessionId, String username, String password);

  Optional<User> isAuthenticated(String sessionId);

  void logout(String sessionId);
}
