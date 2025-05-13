package com.banking.ing.credit.creditservice.credit.exception;

public class CreditException extends RuntimeException {

  public CreditException(String message) {
    super("Credit Exception: " + message);
  }

}
