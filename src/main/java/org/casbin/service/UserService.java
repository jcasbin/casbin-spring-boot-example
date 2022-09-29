// Copyright 2022 The casbin Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.casbin.service;

import org.casbin.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements IUserService {
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final Map<String, User> sessions;
  private final Map<String, User> users;

  public UserService() {
    this.sessions = new ConcurrentHashMap<>();
    this.users = new HashMap<>();
    this.users.put("admin", new User("admin", "pwd"));
    this.users.put("user", new User("user", "pwd"));
  }

  @Override
  public Optional<User> login(String sessionId, String username, String password) {
    User u = users.get(username);
    if (u != null && u.verifyPassword(password)) {
      logger.info("login OK: {} {}", sessionId, username);
      sessions.put(sessionId, u);
      return Optional.of(u);
    }
    logger.info("login Failed: {} {}", sessionId, username);
    return Optional.empty();
  }

  @Override
  public Optional<User> isAuthenticated(String sessionId) {
    return Optional.ofNullable(sessions.get(sessionId));
  }

  @Override
  public void logout(String sessionId) {
    logger.info("logout: {}", sessionId);
    sessions.remove(sessionId);
  }
}
