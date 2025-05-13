package com.banking.ing.credit.creditservice.security.security;

import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_AUTHENTICATION_FAILED;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_AUTHENTICATION_TOKEN_EXPIRED;
import static com.banking.ing.credit.creditservice.common.util.ExceptionHandlerUtil.handleResponse;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.banking.ing.credit.creditservice.common.api.response.ErrorResponse;
import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(final HttpServletRequest request, final HttpServletResponse response,
      final AuthenticationException authException) throws IOException {
    Throwable cause = authException.getCause();
    String message = MESSAGE_AUTHENTICATION_FAILED;
    HttpStatus status = UNAUTHORIZED;
    if (cause instanceof ExpiredJwtException) {
      message = MESSAGE_AUTHENTICATION_TOKEN_EXPIRED;
      status = FORBIDDEN;
    }
    ApiResponse<ErrorResponse> error = handleResponse(authException.getMessage(), message, status);
    response.setStatus(status.value());
    response.setContentType(APPLICATION_JSON_VALUE);
    objectMapper.writeValue(response.getWriter(), error);
  }
}
