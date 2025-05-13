package com.banking.ing.credit.creditservice.credit.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.banking.ing.credit.creditservice.common.exception.base.ResourceAlreadyException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class CustomerCreditAlreadyExistsException extends ResourceAlreadyException {

  public CustomerCreditAlreadyExistsException(final Long customerId) {
    super("Credit with customerId '" + customerId + "' already exists");
  }
}