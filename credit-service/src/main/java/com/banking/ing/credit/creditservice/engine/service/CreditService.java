package com.banking.ing.credit.creditservice.engine.service;

import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanInstallmentsListResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanListResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentResponse;

public interface CreditService {

  String version();

  CustomerLoanCreateResponse createLoanWithInstallments(final Long customerId, final CustomerLoanCreateRequest request);

  CustomerLoanListResponse getLoansByCustomer(final Long customerId);

  CustomerLoanInstallmentsListResponse getInstallmentsByLoan(final Long loanId);

  CustomerLoanInstallmentsListResponse getInstallmentsByCustomerLoan(final Long customerId, final Long loanId);

  CustomerLoanPaymentResponse payInstallments(final CustomerLoanPaymentRequest request);

}
