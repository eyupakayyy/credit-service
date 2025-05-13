package com.banking.ing.credit.creditservice.engine.rest.v1;

import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_DEFAULT_CUSTOMER_CREDIT_INSTALLMENT_PAYMENT_DONE_SUCCESSFULLY;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_DEFAULT_RESOURCE_FOUND_WITH_COUNT;
import static com.banking.ing.credit.creditservice.engine.constants.CreditEngineMessages.MESSAGE_DEFAULT_CREDIT_SUCCESSFULLY_CREATED;
import static java.lang.String.format;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanInstallmentsListResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanListResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentResponse;
import com.banking.ing.credit.creditservice.engine.api.CreditApi;
import com.banking.ing.credit.creditservice.engine.service.CreditService;
import com.banking.ing.credit.creditservice.user.rest.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreditController extends BaseController implements CreditApi {

  private final CreditService service;

  @Override
  public ResponseEntity<ApiResponse<String>> version() {
    return ResponseEntity.ok(
        ApiResponse.<String>builder()
            .success(true)
            .data(service.version())
            .message(String.format("Api version: %s", service.version()))
            .build()
    );
  }

  @Override
  public ResponseEntity<ApiResponse<CustomerLoanCreateResponse>> createLoanWithInstallments(final CustomerLoanCreateRequest request) {
    CustomerLoanCreateResponse result = service.createLoanWithInstallments(request.getCustomerId(), request);
    return ResponseEntity.ok(
        ApiResponse.<CustomerLoanCreateResponse>builder()
            .success(true)
            .data(result)
            .message(MESSAGE_DEFAULT_CREDIT_SUCCESSFULLY_CREATED)
            .build()
    );
  }

  @Override
  public ResponseEntity<ApiResponse<CustomerLoanListResponse>> getLoansByCustomer(final Long customerId) {
    CustomerLoanListResponse result = service.getLoansByCustomer(customerId);
    return ResponseEntity.ok(
        ApiResponse.<CustomerLoanListResponse>builder()
            .success(true)
            .data(result)
            .message(MESSAGE_DEFAULT_CREDIT_SUCCESSFULLY_CREATED)
            .build()
    );
  }

  @Override
  public ResponseEntity<ApiResponse<CustomerLoanInstallmentsListResponse>> getInstallmentsByCustomerLoan(final Long customerId, final Long loanId) {
    CustomerLoanInstallmentsListResponse result = service.getInstallmentsByCustomerLoan(customerId, loanId);
    return ResponseEntity.ok(
        ApiResponse.<CustomerLoanInstallmentsListResponse>builder()
            .success(true)
            .data(result)
            .message(format(MESSAGE_DEFAULT_RESOURCE_FOUND_WITH_COUNT, result.getInstallments().size()))
            .build()
    );
  }

  @Override
  public ResponseEntity<ApiResponse<CustomerLoanInstallmentsListResponse>> getInstallmentsByLoan(final Long loanId) {
    CustomerLoanInstallmentsListResponse result = service.getInstallmentsByLoan(loanId);
    return ResponseEntity.ok(
        ApiResponse.<CustomerLoanInstallmentsListResponse>builder()
            .success(true)
            .data(result)
            .message(format(MESSAGE_DEFAULT_RESOURCE_FOUND_WITH_COUNT, result.getInstallments().size()))
            .build()
    );
  }

  @Override
  public ResponseEntity<ApiResponse<CustomerLoanPaymentResponse>> payInstallments(final CustomerLoanPaymentRequest request) {
    CustomerLoanPaymentResponse result = service.payInstallments(request);
    return ResponseEntity.ok(
        ApiResponse.<CustomerLoanPaymentResponse>builder()
            .success(true)
            .data(result)
            .message(format(MESSAGE_DEFAULT_CUSTOMER_CREDIT_INSTALLMENT_PAYMENT_DONE_SUCCESSFULLY, result.getPaidInstallments().size()))
            .build()
    );
  }

}
