package com.banking.ing.credit.creditservice.engine.exception;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

import com.banking.ing.credit.creditservice.credit.exception.CreditException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_ACCEPTABLE)
public class CreditInstallmentPaymentEligibilityException extends CreditException {

  public CreditInstallmentPaymentEligibilityException(final String msg) {
    super("Credit payment credentials is not eligible to continue!" + msg);
  }

  public CreditInstallmentPaymentEligibilityException() {
    super("Credit payment credentials is not eligible to continue!");
  }

}