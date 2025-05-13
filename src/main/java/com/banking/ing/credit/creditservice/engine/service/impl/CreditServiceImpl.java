package com.banking.ing.credit.creditservice.engine.service.impl;

import static com.banking.ing.credit.creditservice.engine.util.LoanUtil.isLoanRequestFeasible;
import static com.banking.ing.credit.creditservice.engine.util.LoanUtil.isPaymentOfLoanInstallmentRequestFeasible;

import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import com.banking.ing.credit.creditservice.credit.mapper.LoanInstallmentObjectMapper;
import com.banking.ing.credit.creditservice.credit.mapper.LoanObjectMapper;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanInstallmentsListResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanListResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentResponse;
import com.banking.ing.credit.creditservice.credit.service.LoanInstallmentService;
import com.banking.ing.credit.creditservice.credit.service.LoanService;
import com.banking.ing.credit.creditservice.engine.exception.CreditEligibilityException;
import com.banking.ing.credit.creditservice.engine.exception.CreditInstallmentPaymentEligibilityException;
import com.banking.ing.credit.creditservice.engine.exception.CustomerCreditEvaluationException;
import com.banking.ing.credit.creditservice.engine.exception.CustomerEvaluationException;
import com.banking.ing.credit.creditservice.engine.service.CreditService;
import com.banking.ing.credit.creditservice.engine.service.CustomerCreditEvaluationService;
import com.banking.ing.credit.creditservice.engine.service.LoanEngineService;
import com.banking.ing.credit.creditservice.engine.service.LoanEvaluationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

  private static final String API_VERSION = "1.1.0";

  private final LoanEvaluationService loanEvaluationService;
  private final CustomerCreditEvaluationService creditEvaluationService;
  private final LoanEngineService loanEngineService;
  private final LoanService loanService;
  private final LoanInstallmentService loanInstallmentService;
  private final LoanObjectMapper loanObjectMapper;
  private final LoanInstallmentObjectMapper loanInstallmentObjectMapper;

  @Override
  public String version() {
    return API_VERSION;
  }

  @Override
  public CustomerLoanCreateResponse createLoanWithInstallments(final Long customerId, final CustomerLoanCreateRequest request) {
    validateRequestInput(request);
    validateCreditCredentialsAcceptance(request);
    evaluateCustomerEligibilityForLoan(customerId, request);
    return processCredit(request);
  }

  @Override
  public CustomerLoanListResponse getLoansByCustomer(final Long customerId) {
    List<LoanEntity> loans = loanService.findAllByCustomerId(customerId);
    return CustomerLoanListResponse.builder()
        .customerId(customerId)
        .loans(loanObjectMapper.toDtoList(loans))
        .build();
  }

  @Override
  public CustomerLoanInstallmentsListResponse getInstallmentsByCustomerLoan(final Long customerId, final Long loanId) {
    List<LoanInstallmentEntity> installments = loanInstallmentService.findAllByLoanId(loanId);
    return CustomerLoanInstallmentsListResponse.builder()
        .customerId(customerId)
        .installments(loanInstallmentObjectMapper.toDtoList(installments))
        .build();
  }

  @Override
  public CustomerLoanInstallmentsListResponse getInstallmentsByLoan(final Long loanId) {
    List<LoanInstallmentEntity> installments = loanInstallmentService.findAllByLoanId(loanId);
    LoanEntity loan = loanService.findById(loanId);
    return CustomerLoanInstallmentsListResponse.builder()
        .customerId(loan.getCustomer().getId())
        .installments(loanInstallmentObjectMapper.toDtoList(installments))
        .build();
  }

  @Override
  public CustomerLoanPaymentResponse payInstallments(final CustomerLoanPaymentRequest request) {
    validateRequestInput(request);
    return loanEngineService.paymentProcess(request);
  }

  private void validateRequestInput(final CustomerLoanCreateRequest request) {
    if (!isLoanRequestFeasible(request)) {
      throw new CreditEligibilityException();
    }
  }

  private void validateRequestInput(final CustomerLoanPaymentRequest request) {
    if (!isPaymentOfLoanInstallmentRequestFeasible(request)) {
      throw new CreditInstallmentPaymentEligibilityException();
    }
  }

  private void evaluateCustomerEligibilityForLoan(final Long customerId, final CustomerLoanCreateRequest request) {
    if (!creditEvaluationService.isCustomerEligible(customerId, request.getAmount())) {
      throw new CustomerEvaluationException(customerId, request.getAmount());
    }
    if (!creditEvaluationService.evaluateCustomerLoanLimit(customerId, request.getAmount())) {
      throw new CustomerCreditEvaluationException(customerId, request.getAmount());
    }
  }

  private void validateCreditCredentialsAcceptance(final CustomerLoanCreateRequest request) {
    loanEvaluationService.isCreditInterestFeasible(request.getInterestRate());
    loanEvaluationService.isInstallmentFeasible(request.getInstallmentCount());
  }

  private CustomerLoanCreateResponse processCredit(final CustomerLoanCreateRequest request) {
    return loanEngineService.initiateLoan(request);
  }

}
