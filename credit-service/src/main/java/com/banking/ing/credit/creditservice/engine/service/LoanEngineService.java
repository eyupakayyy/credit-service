package com.banking.ing.credit.creditservice.engine.service;

import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentResponse;

public interface LoanEngineService {

  CustomerLoanCreateResponse initiateLoan(final CustomerLoanCreateRequest request);

  CustomerLoanPaymentResponse paymentProcess(final CustomerLoanPaymentRequest request);

}
