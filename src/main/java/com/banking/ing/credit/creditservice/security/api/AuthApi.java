package com.banking.ing.credit.creditservice.security.api;

import static com.banking.ing.credit.creditservice.security.constants.AuthRouteConstants.AUTH_BASE_URI;
import static com.banking.ing.credit.creditservice.security.constants.AuthRouteConstants.LOGIN_URI;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.security.modal.AuthRequest;
import com.banking.ing.credit.creditservice.security.modal.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(AUTH_BASE_URI)
public interface AuthApi {

  @PostMapping(LOGIN_URI)
  ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody final AuthRequest authRequest);

}
