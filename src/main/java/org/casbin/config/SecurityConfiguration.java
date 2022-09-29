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
package org.casbin.config;

import org.casbin.jcasbin.main.Enforcer;
import org.casbin.service.IUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.inject.Inject;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
  private final Enforcer enforcer;
  private final IUserService userAccessService;

  @Inject
  public SecurityConfiguration(Enforcer enforcer, IUserService userAccessService) {
    this.enforcer = enforcer;
    this.userAccessService = userAccessService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .addFilterBefore(new CasbinFilter(enforcer, userAccessService), BasicAuthenticationFilter.class)
      .antMatcher("/data/**")
      .csrf().disable();
    return http.build();
  }
}
