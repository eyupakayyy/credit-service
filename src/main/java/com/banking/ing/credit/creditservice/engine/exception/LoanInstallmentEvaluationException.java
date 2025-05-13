package com.banking.ing.credit.creditservice.engine.exception;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

import com.banking.ing.credit.creditservice.credit.exception.CreditException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_ACCEPTABLE)
public class LoanInstallmentEvaluationException extends CreditException {

  public LoanInstallmentEvaluationException(final int installmentCount) {
    super("Loan with amount this installmentCount  '" + installmentCount + "' is not acceptable ");
  }

}