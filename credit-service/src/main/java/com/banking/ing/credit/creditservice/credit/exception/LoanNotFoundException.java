package com.banking.ing.credit.creditservice.credit.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.banking.ing.credit.creditservice.common.exception.base.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class LoanNotFoundException extends ResourceNotFoundException {

  public LoanNotFoundException(final Long id) {
    super("Loan with ID '" + id + "' not found");
  }
}