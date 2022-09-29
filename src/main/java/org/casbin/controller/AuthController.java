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
