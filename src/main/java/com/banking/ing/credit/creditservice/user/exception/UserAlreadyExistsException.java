package com.banking.ing.credit.creditservice.user.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import com.banking.ing.credit.creditservice.common.exception.base.ResourceAlreadyException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(CONFLICT)
public class UserAlreadyExistsException extends ResourceAlreadyException {

  public UserAlreadyExistsException(final String username) {
    super("User already exists with username: " + username);
  }

}