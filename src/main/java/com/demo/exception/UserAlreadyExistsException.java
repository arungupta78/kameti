package com.demo.exception;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String errorMessage) {
    super(errorMessage);
  }
}
