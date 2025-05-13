package com.banking.ing.credit.creditservice.security.service;

import com.banking.ing.credit.creditservice.security.modal.AuthRequest;

public interface AuthenticationService {

  String login(final AuthRequest authRequest);

}
