package org.casbin.service;

import org.casbin.model.Data;

public interface IDataService {
  void setState(String state);

  Data getSecuredData(String source);
}
