package com.banking.ing.credit.creditservice.engine.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateResponse;
import com.banking.ing.credit.creditservice.engine.exception.CreditEligibilityException;
import com.banking.ing.credit.creditservice.engine.exception.CustomerCreditEvaluationException;
import com.banking.ing.credit.creditservice.engine.exception.CustomerEvaluationException;
import com.banking.ing.credit.creditservice.engine.service.impl.CreditServiceImpl;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

  @InjectMocks
  private CreditServiceImpl creditService;

  @Mock
  private LoanEvaluationService loanEvaluationService;

  @Mock
  private CustomerCreditEvaluationService creditEvaluationService;

  @Mock
  private LoanEngineService loanEngineService;

  private CustomerLoanCreateRequest request;

  @BeforeEach
  void setup() {
    request = CustomerLoanCreateRequest.builder()
        .customerId(1L)
        .amount(BigDecimal.valueOf(5000))
        .installmentCount(12)
        .interestRate(BigDecimal.valueOf(5.5))
        .build();
  }

  @Test
  void shouldCreateLoanSuccessfully() {
    Long customerId = 1L;
    CustomerLoanCreateResponse expected = CustomerLoanCreateResponse.builder().build();

    when(creditEvaluationService.isCustomerEligible(customerId, request.getAmount())).thenReturn(true);
    when(creditEvaluationService.evaluateCustomerLoanLimit(customerId, request.getAmount())).thenReturn(true);
    when(loanEngineService.initiateLoan(request)).thenReturn(expected);

    CustomerLoanCreateResponse response = creditService.createLoanWithInstallments(customerId, request);

    assertNotNull(response);
    verify(loanEvaluationService).isCreditInterestFeasible(request.getInterestRate());
    verify(loanEvaluationService).isInstallmentFeasible(request.getInstallmentCount());
    verify(creditEvaluationService).isCustomerEligible(customerId, request.getAmount());
    verify(creditEvaluationService).evaluateCustomerLoanLimit(customerId, request.getAmount());
    verify(loanEngineService).initiateLoan(request);
  }

  @Test
  void shouldThrowExceptionWhenLoanNotFeasible() {
    request.setAmount(BigDecimal.ZERO);

    assertThrows(CreditEligibilityException.class, () ->
        creditService.createLoanWithInstallments(1L, request));
  }

  @Test
  void shouldThrowExceptionWhenCustomerNotEligible() {
    Long customerId = 2L;

    when(creditEvaluationService.isCustomerEligible(customerId, request.getAmount())).thenReturn(false);

    assertThrows(CustomerEvaluationException.class, () ->
        creditService.createLoanWithInstallments(customerId, request));
  }

  @Test
  void shouldThrowExceptionWhenLoanLimitExceeded() {
    Long customerId = 3L;

    when(creditEvaluationService.isCustomerEligible(customerId, request.getAmount())).thenReturn(true);
    when(creditEvaluationService.evaluateCustomerLoanLimit(customerId, request.getAmount())).thenReturn(false);

    assertThrows(CustomerCreditEvaluationException.class, () ->
        creditService.createLoanWithInstallments(customerId, request));
  }
}
