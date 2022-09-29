package org.casbin.model;

public record Data(
  String source,
  String data,
  long timestamp,
  String state
) {
}
