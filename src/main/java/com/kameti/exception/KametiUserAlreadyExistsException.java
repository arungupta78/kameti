package com.kameti.exception;

public class KametiUserAlreadyExistsException extends RuntimeException {
  public KametiUserAlreadyExistsException(String errorMessage) {
    super(errorMessage);
  }
}
