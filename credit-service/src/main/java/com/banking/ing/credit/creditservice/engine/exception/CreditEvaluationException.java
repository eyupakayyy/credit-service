package com.banking.ing.credit.creditservice.engine.exception;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

import com.banking.ing.credit.creditservice.credit.exception.CreditException;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_ACCEPTABLE)
public class CreditEvaluationException extends CreditException {

  public CreditEvaluationException(final Long customerID, final BigDecimal amount) {
    super("Credit with amount of  '" + amount + "' is not acceptable for user " + customerID);
  }

}