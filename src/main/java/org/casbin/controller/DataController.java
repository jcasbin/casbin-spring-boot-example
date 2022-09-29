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

import org.casbin.model.Data;
import org.casbin.service.IDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;


@RestController
@RequestMapping("/data")
public class DataController {

  private static final Logger logger = LoggerFactory.getLogger(DataController.class);

  private final IDataService dataService;

  @Inject
  public DataController(IDataService dataService) {
    this.dataService = dataService;
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/users/all")
  public ResponseEntity<Data> getForUsersData(Authentication authentication) {
    logger.info("getData: authentication={}", authentication.getName());
    authentication.getAuthorities().forEach(a -> {
      logger.info("  authority={}", a.getAuthority());
    });
    return ResponseEntity.ok().body(
      dataService.getSecuredData("Secured for USER/ADMIN " + authentication.getName()));
  }

  @Secured("ROLE_ADMIN")
  @GetMapping("/admins/all")
  public ResponseEntity<Data> getDataForAdmins(Authentication authentication) {
    logger.info("getData: authentication={}", authentication.getName());
    authentication.getAuthorities().forEach(a -> {
      logger.info("  authority={}", a.getAuthority());
    });
    return ResponseEntity.ok()
      .body(dataService.getSecuredData("Secured for ADMIN " + authentication.getName()));
  }

  @Secured("ROLE_ADMIN")
  @PutMapping("/admins/state/{state}")
  public ResponseEntity<Data> setStateForAdmins(Authentication authentication,
                                                @PathVariable String state) {
    logger.info("setState: authentication={} state={}", authentication.getName(), state);
    authentication.getAuthorities().forEach(a -> {
      logger.info("  authority={}", a.getAuthority());
    });
    dataService.setState(state);
    return ResponseEntity.ok().build();
  }

}
