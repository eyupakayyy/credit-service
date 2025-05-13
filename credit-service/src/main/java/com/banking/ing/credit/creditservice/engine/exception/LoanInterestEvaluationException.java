package com.banking.ing.credit.creditservice.engine.exception;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

import com.banking.ing.credit.creditservice.credit.exception.CreditException;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_ACCEPTABLE)
public class LoanInterestEvaluationException extends CreditException {

  public LoanInterestEvaluationException(final BigDecimal interestRate) {
    super("Loan with amount this interest  '" + interestRate + "' is not acceptable ");
  }

}