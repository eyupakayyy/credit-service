package com.banking.ing.credit.creditservice.security.rest.v1;

import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_DEFAULT_RESOURCE_SUCCESSFULLY_UPDATED;
import static java.lang.String.format;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.security.api.AuthApi;
import com.banking.ing.credit.creditservice.security.modal.AuthRequest;
import com.banking.ing.credit.creditservice.security.modal.AuthResponse;
import com.banking.ing.credit.creditservice.security.rest.BaseController;
import com.banking.ing.credit.creditservice.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController extends BaseController implements AuthApi {

  private final AuthenticationService service;

  @Override
  public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody final AuthRequest authRequest) {
    String token = service.login(authRequest);
    return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
        .success(true)
        .data(AuthResponse.builder()
            .token(token)
            .build())
        .message(format(MESSAGE_DEFAULT_RESOURCE_SUCCESSFULLY_UPDATED))
        .build());
  }
}
