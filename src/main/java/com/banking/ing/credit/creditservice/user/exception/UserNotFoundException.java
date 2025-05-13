package com.banking.ing.credit.creditservice.user.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.banking.ing.credit.creditservice.common.exception.base.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class UserNotFoundException extends ResourceNotFoundException {

  public UserNotFoundException(String username) {
    super("User '" + username + "' not found");
  }

}