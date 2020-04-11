package com.emse.airportSystem.web.model;

public class ApiError {

  private String code;
  private String message;
  private String description;

  public String getCode() {
    return code;
  }

  public ApiError code(String code) {
    this.code = code;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public ApiError message(String message) {
    this.message = message;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public ApiError description(String description) {
    this.description = description;
    return this;
  }
}
