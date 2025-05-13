package com.banking.ing.credit.creditservice.engine.api;

import static com.banking.ing.credit.creditservice.engine.constants.CreditRouteConstants.CREDIT_BASE_URI;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanInstallmentsListResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanListResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(CREDIT_BASE_URI)
public interface CreditApi {

  @GetMapping("/version")
  ResponseEntity<ApiResponse<String>> version();

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<CustomerLoanCreateResponse>> createLoanWithInstallments(@Valid @RequestBody final CustomerLoanCreateRequest request);

  @GetMapping("/{customerId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<CustomerLoanListResponse>> getLoansByCustomer(@PathVariable final Long customerId);

  @GetMapping("/installments/{customerId}/{loanId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<CustomerLoanInstallmentsListResponse>> getInstallmentsByCustomerLoan(@PathVariable final Long customerId,
      @PathVariable final Long loanId);

  @GetMapping("/installments/{loanId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<CustomerLoanInstallmentsListResponse>> getInstallmentsByLoan(@PathVariable final Long loanId);

  @PostMapping("/payment")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<CustomerLoanPaymentResponse>> payInstallments(@RequestBody final CustomerLoanPaymentRequest request);

}
