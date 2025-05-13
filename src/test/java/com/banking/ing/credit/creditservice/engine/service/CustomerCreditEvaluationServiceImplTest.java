package com.banking.ing.credit.creditservice.engine.service;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.ing.credit.creditservice.credit.entity.CustomerCreditEntity;
import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import com.banking.ing.credit.creditservice.credit.service.CustomerCreditService;
import com.banking.ing.credit.creditservice.credit.service.LoanInstallmentService;
import com.banking.ing.credit.creditservice.credit.service.LoanService;
import com.banking.ing.credit.creditservice.engine.service.impl.CustomerCreditEvaluationServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerCreditEvaluationServiceImplTest {

  private CustomerCreditService customerCreditService;
  private CustomerFraudService fraudService;
  private CustomerCreditScoreService creditScoreService;
  private LoanService loanService;
  private LoanInstallmentService installmentService;

  private CustomerCreditEvaluationServiceImpl service;

  @BeforeEach
  void setUp() {
    customerCreditService = mock(CustomerCreditService.class);
    fraudService = mock(CustomerFraudService.class);
    creditScoreService = mock(CustomerCreditScoreService.class);
    loanService = mock(LoanService.class);
    installmentService = mock(LoanInstallmentService.class);

    service = new CustomerCreditEvaluationServiceImpl(
        customerCreditService,
        fraudService,
        creditScoreService,
        loanService,
        installmentService
    );
  }

  // --- isCustomerEligible ---

  @Test
  void shouldReturnTrueWhenFraudAndCreditScoreAreValid() {
    when(fraudService.customerFraudEvaluation(1L)).thenReturn(true);
    when(creditScoreService.customerCreditScoreEvaluation(1L)).thenReturn(true);

    boolean result = service.isCustomerEligible(1L, BigDecimal.valueOf(1000));
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenFraudCheckFails() {
    when(fraudService.customerFraudEvaluation(1L)).thenReturn(false);
    when(creditScoreService.customerCreditScoreEvaluation(1L)).thenReturn(true);

    boolean result = service.isCustomerEligible(1L, BigDecimal.valueOf(1000));
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenCreditScoreFails() {
    when(fraudService.customerFraudEvaluation(1L)).thenReturn(true);
    when(creditScoreService.customerCreditScoreEvaluation(1L)).thenReturn(false);

    boolean result = service.isCustomerEligible(1L, BigDecimal.valueOf(1000));
    assertThat(result).isFalse();
  }

  // --- evaluateCustomerLoanLimit ---

  @Test
  void shouldReturnTrueWhenUsableCreditLimitIsPositive() {
    CustomerCreditEntity credit = new CustomerCreditEntity();
    credit.setCreditLimit(new BigDecimal("10000"));

    LoanEntity loan1 = new LoanEntity();
    loan1.setLoanAmount(new BigDecimal("4000"));

    LoanEntity loan2 = new LoanEntity();
    loan2.setLoanAmount(new BigDecimal("1000"));

    LoanInstallmentEntity inst1 = new LoanInstallmentEntity();
    inst1.setAmount(new BigDecimal("700"));
    inst1.setPaid(true);

    LoanInstallmentEntity inst2 = new LoanInstallmentEntity();
    inst2.setAmount(new BigDecimal("300"));
    inst2.setPaid(false); // should be ignored

    when(customerCreditService.fetchByCustomerId(1L)).thenReturn(credit);
    when(loanService.findAllByCustomerId(1L)).thenReturn(List.of(loan1, loan2));
    when(installmentService.findAllByCustomerId(1L)).thenReturn(List.of(inst1, inst2));

    boolean result = service.evaluateCustomerLoanLimit(1L, BigDecimal.valueOf(1000));
    assertThat(result).isTrue(); // 10000 - 5000 + 700 = 5700 > 0
  }

  @Test
  void shouldReturnFalseWhenUsableCreditLimitIsZeroOrLess() {
    CustomerCreditEntity credit = new CustomerCreditEntity();
    credit.setCreditLimit(new BigDecimal("1000"));

    LoanEntity loan = new LoanEntity();
    loan.setLoanAmount(new BigDecimal("1500"));

    LoanInstallmentEntity inst = new LoanInstallmentEntity();
    inst.setAmount(new BigDecimal("200"));
    inst.setPaid(true);

    when(customerCreditService.fetchByCustomerId(1L)).thenReturn(credit);
    when(loanService.findAllByCustomerId(1L)).thenReturn(List.of(loan));
    when(installmentService.findAllByCustomerId(1L)).thenReturn(List.of(inst));

    boolean result = service.evaluateCustomerLoanLimit(1L, BigDecimal.valueOf(1000));
    assertThat(result).isFalse(); // 1000 - 1500 + 200 = -300 <= 0
  }

  @Test
  void shouldHandleNullLoanAmountsAndInstallmentsGracefully() {
    CustomerCreditEntity credit = new CustomerCreditEntity();
    credit.setCreditLimit(new BigDecimal("2000"));

    // Loan with null amount
    LoanEntity loan = new LoanEntity();
    loan.setLoanAmount(ZERO);

    // Paid installment with null amount
    LoanInstallmentEntity installment = new LoanInstallmentEntity();
    installment.setPaid(true);
    installment.setAmount(ZERO);

    when(customerCreditService.fetchByCustomerId(1L)).thenReturn(credit);
    when(loanService.findAllByCustomerId(1L)).thenReturn(List.of(loan));
    when(installmentService.findAllByCustomerId(1L)).thenReturn(List.of(installment));

    boolean result = service.evaluateCustomerLoanLimit(1L, BigDecimal.valueOf(1000));

    assertThat(result).isTrue(); // creditLimit is 2000, no loan used, no installment paid => usable limit = 2000

    // ✅ Verifying interactions
    verify(customerCreditService).fetchByCustomerId(1L);
    verify(loanService).findAllByCustomerId(1L);
    verify(installmentService).findAllByCustomerId(1L);
  }

  @Test
  void shouldHandleNullListsAndReturnSafeDefaults() {
    CustomerCreditEntity credit = new CustomerCreditEntity();
    credit.setCreditLimit(new BigDecimal("3000"));

    when(customerCreditService.fetchByCustomerId(1L)).thenReturn(credit);
    when(loanService.findAllByCustomerId(1L)).thenReturn(List.of()); // no loans
    when(installmentService.findAllByCustomerId(1L)).thenReturn(List.of()); // no installments

    boolean result = service.evaluateCustomerLoanLimit(1L, BigDecimal.valueOf(1000));
    assertThat(result).isTrue(); // 3000 - 0 + 0 = 3000 > 0
  }
}
