package com.banking.ing.credit.creditservice.common.exception.base;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super("Resource not found: " + message);
  }

}