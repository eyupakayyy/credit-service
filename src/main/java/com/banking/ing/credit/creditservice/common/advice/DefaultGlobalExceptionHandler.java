package com.banking.ing.credit.creditservice.common.advice;

import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_AUTHENTICATION_FAILED;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_AUTHENTICATION_FAILED_DUE_TO_WRONG_PASSWORD;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_AUTHENTICATION_NO_RIGHT_TO_ACCESS_THIS_ENTRY_POINT;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_AUTHENTICATION_TOKEN_EXPIRED;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_DEFAULT_CREDIT_FAILURE;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_DEFAULT_CREDIT_FAILURE_DUE_TO_INVALID_INSTALLMENT_REQUEST;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_DEFAULT_CREDIT_FAILURE_DUE_TO_INVALID_INTEREST_REQUEST;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_DEFAULT_CUSTOMER_CREDIT_DEFINITION_FAILED;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_DEFAULT_RESOURCE_ALREADY_EXISTS;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_DEFAULT_RESOURCE_NOT_FOUND;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_NO_ACTIVE_USER_FOUND_MIGHT_BE_DELETED;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_UNEXPECTED_ERROR;
import static com.banking.ing.credit.creditservice.common.constants.InfrastructureConstants.BASE_SERVICE_PACKAGE;
import static com.banking.ing.credit.creditservice.common.util.ExceptionHandlerUtil.handleResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.banking.ing.credit.creditservice.common.api.response.ErrorResponse;
import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.common.exception.base.ResourceAlreadyException;
import com.banking.ing.credit.creditservice.common.exception.base.ResourceNotFoundException;
import com.banking.ing.credit.creditservice.credit.exception.CreditException;
import com.banking.ing.credit.creditservice.credit.exception.CreditNotFoundException;
import com.banking.ing.credit.creditservice.engine.exception.CustomerCreditEvaluationException;
import com.banking.ing.credit.creditservice.engine.exception.LoanInstallmentEvaluationException;
import com.banking.ing.credit.creditservice.engine.exception.LoanInterestEvaluationException;
import com.banking.ing.credit.creditservice.user.exception.UserAlreadyExistsException;
import com.banking.ing.credit.creditservice.user.exception.UserNotFoundException;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice(basePackages = BASE_SERVICE_PACKAGE)
public class DefaultGlobalExceptionHandler {

  private final Gson gson;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleGeneric(final Exception ex) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(handleResponse(ex, MESSAGE_UNEXPECTED_ERROR, INTERNAL_SERVER_ERROR));
  }

  @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
  public ResponseEntity<ApiResponse<ErrorResponse>> handleAccessDenied(final AccessDeniedException ex) {
    return ResponseEntity.status(FORBIDDEN).body(handleResponse(ex, MESSAGE_AUTHENTICATION_NO_RIGHT_TO_ACCESS_THIS_ENTRY_POINT, FORBIDDEN));
  }

  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleExpiredToken(final ExpiredJwtException ex) {
    return ResponseEntity.status(FORBIDDEN).body(handleResponse(ex, MESSAGE_AUTHENTICATION_TOKEN_EXPIRED, FORBIDDEN));
  }

  @ExceptionHandler({AuthenticationException.class})
  public ResponseEntity<ApiResponse<ErrorResponse>> handleAuthenticationFailed(final AuthenticationException ex) {
    return ResponseEntity.status(FORBIDDEN).body(handleResponse(ex, MESSAGE_AUTHENTICATION_FAILED, FORBIDDEN));
  }

  @ExceptionHandler({BadCredentialsException.class})
  public ResponseEntity<ApiResponse<ErrorResponse>> handleBadCredentials(final BadCredentialsException ex) {
    return ResponseEntity.status(FORBIDDEN).body(handleResponse(ex, MESSAGE_AUTHENTICATION_FAILED_DUE_TO_WRONG_PASSWORD, FORBIDDEN));
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleUserExists(final UserAlreadyExistsException ex) {
    return ResponseEntity.status(CONFLICT).body(handleResponse(ex, MESSAGE_DEFAULT_RESOURCE_ALREADY_EXISTS, CONFLICT));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleUserNotFound(final UserNotFoundException ex) {
    return ResponseEntity.status(NOT_FOUND).body(handleResponse(ex, MESSAGE_DEFAULT_RESOURCE_NOT_FOUND, NOT_FOUND));
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleNotFoundActiveUser(final UsernameNotFoundException ex) {
    return ResponseEntity.status(NOT_FOUND).body(handleResponse(ex, MESSAGE_NO_ACTIVE_USER_FOUND_MIGHT_BE_DELETED, NOT_FOUND));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleGenericNotFoundException(final ResourceNotFoundException ex) {
    return ResponseEntity.status(NOT_FOUND).body(handleResponse(ex, MESSAGE_DEFAULT_RESOURCE_NOT_FOUND, NOT_FOUND));
  }

  @ExceptionHandler(ResourceAlreadyException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleGenericAlreadyExistsException(final ResourceAlreadyException ex) {
    return ResponseEntity.status(NOT_FOUND).body(handleResponse(ex, MESSAGE_DEFAULT_RESOURCE_ALREADY_EXISTS, NOT_FOUND));
  }

  @ExceptionHandler(CreditException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleGenericCreditException(final CreditException ex) {
    return ResponseEntity.status(BAD_REQUEST).body(handleResponse(ex, MESSAGE_DEFAULT_CREDIT_FAILURE, BAD_REQUEST));
  }

  @ExceptionHandler(CustomerCreditEvaluationException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleCreditEvaluationException(final CustomerCreditEvaluationException ex) {
    return ResponseEntity.status(FORBIDDEN).body(handleResponse(ex, MESSAGE_DEFAULT_CREDIT_FAILURE, FORBIDDEN));
  }

  @ExceptionHandler(LoanInterestEvaluationException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleCreditEvaluationException(final LoanInterestEvaluationException ex) {
    return ResponseEntity.status(BAD_REQUEST).body(handleResponse(ex, MESSAGE_DEFAULT_CREDIT_FAILURE_DUE_TO_INVALID_INTEREST_REQUEST, BAD_REQUEST));
  }

  @ExceptionHandler(LoanInstallmentEvaluationException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleCreditEvaluationException(final LoanInstallmentEvaluationException ex) {
    return ResponseEntity.status(BAD_REQUEST)
        .body(handleResponse(ex, MESSAGE_DEFAULT_CREDIT_FAILURE_DUE_TO_INVALID_INSTALLMENT_REQUEST, BAD_REQUEST));
  }

  @ExceptionHandler(CreditNotFoundException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleCustomerCreditEvaluationException(final CreditNotFoundException ex) {
    return ResponseEntity.status(FORBIDDEN).body(handleResponse(ex, MESSAGE_DEFAULT_CUSTOMER_CREDIT_DEFINITION_FAILED, FORBIDDEN));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.status(BAD_REQUEST).body(handleResponse(ex, gson.toJson(errors), BAD_REQUEST));
  }

}
