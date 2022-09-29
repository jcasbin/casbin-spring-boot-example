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
package org.casbin.controller;

import org.casbin.model.User;
import org.casbin.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/auth/")
public class AuthController {
  private final IUserService userAccessService;

  private final HttpSession httpSession;

  @Inject
  public AuthController(IUserService userAccessService, HttpSession httpSession) {
    this.userAccessService = userAccessService;
    this.httpSession = httpSession;
  }

  @GetMapping("/login")
  public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
    Optional<User> u = userAccessService.login(httpSession.getId(), username, password);
    if (u.isPresent()) {
      return ResponseEntity.ok().body(u.get());
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @GetMapping("/logout")
  public ResponseEntity<String> logout() {
    userAccessService.logout(httpSession.getId());
    return ResponseEntity.ok().body("logout success!");
  }
}
