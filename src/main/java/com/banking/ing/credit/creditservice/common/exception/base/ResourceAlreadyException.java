package com.banking.ing.credit.creditservice.common.exception.base;

public class ResourceAlreadyException extends RuntimeException {

  public ResourceAlreadyException(final String message) {
    super("Resource already exists: " + message);
  }

}