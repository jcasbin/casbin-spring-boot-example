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

import org.casbin.model.Data;
import org.springframework.stereotype.Service;

@Service
public class DataService implements IDataService {
  private String state;

  public DataService() {
    this.state = "DEFAULT";
  }

  @Override
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public Data getSecuredData(String source) {
    return new Data(source, "Some Data", System.currentTimeMillis(), state);
  }
}
