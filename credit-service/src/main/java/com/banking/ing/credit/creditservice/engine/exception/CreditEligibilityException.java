package com.banking.ing.credit.creditservice.engine.exception;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

import com.banking.ing.credit.creditservice.credit.exception.CreditException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_ACCEPTABLE)
public class CreditEligibilityException extends CreditException {

  public CreditEligibilityException(final String msg) {
    super("Credit credentials is not eligible to continue!" + msg);
  }

  public CreditEligibilityException() {
    super("Credit credentials is not eligible to continue!");
  }

}