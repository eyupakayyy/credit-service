package com.banking.ing.credit.creditservice.common.util;

import com.banking.ing.credit.creditservice.common.api.response.ErrorResponse;
import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.common.util.base.Utility;
import java.time.Instant;
import org.springframework.http.HttpStatus;

public final class ExceptionHandlerUtil extends Utility {

  public static ApiResponse<ErrorResponse> handleResponse(final Throwable throwable, final String message, final HttpStatus status) {
    return handleResponse(throwable.getMessage(), message, status);
  }

  public static ApiResponse<ErrorResponse> handleResponse(final String error, final String message, final HttpStatus status) {
    return ApiResponse.<ErrorResponse>builder()
        .success(false)
        .data(new ErrorResponse(error, Instant.now().toString(), status.value()))
        .message(message)
        .build();
  }

}
