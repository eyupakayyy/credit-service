package com.banking.ing.credit.creditservice.engine.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.banking.ing.credit.creditservice.engine.exception.LoanInstallmentEvaluationException;
import com.banking.ing.credit.creditservice.engine.exception.LoanInterestEvaluationException;
import com.banking.ing.credit.creditservice.engine.service.impl.LoanEvaluationServiceImpl;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanEvaluationServiceImplTest {

  private LoanEvaluationServiceImpl loanEvaluationService;

  @BeforeEach
  void setup() {
    loanEvaluationService = new LoanEvaluationServiceImpl();
  }

  // --- Interest Rate Tests ---

  @Test
  void shouldAcceptValidInterestRate() {
    BigDecimal validRate = new BigDecimal("0.3");
    assertDoesNotThrow(() -> loanEvaluationService.isCreditInterestFeasible(validRate));
  }

  @Test
  void shouldAcceptMinimumInterestRate() {
    BigDecimal minRate = new BigDecimal("0.1");
    assertDoesNotThrow(() -> loanEvaluationService.isCreditInterestFeasible(minRate));
  }

  @Test
  void shouldAcceptMaximumInterestRate() {
    BigDecimal maxRate = new BigDecimal("0.5");
    assertDoesNotThrow(() -> loanEvaluationService.isCreditInterestFeasible(maxRate));
  }

  @Test
  void shouldThrowForBelowMinimumInterestRate() {
    BigDecimal invalidRate = new BigDecimal("0.09");
    assertThatThrownBy(() -> loanEvaluationService.isCreditInterestFeasible(invalidRate))
        .isInstanceOf(LoanInterestEvaluationException.class)
        .hasMessageContaining("0.09");
  }

  @Test
  void shouldThrowForAboveMaximumInterestRate() {
    BigDecimal invalidRate = new BigDecimal("0.6");
    assertThatThrownBy(() -> loanEvaluationService.isCreditInterestFeasible(invalidRate))
        .isInstanceOf(LoanInterestEvaluationException.class)
        .hasMessageContaining("0.6");
  }

  @Test
  void shouldThrowForNullInterestRate() {
    assertThatThrownBy(() -> loanEvaluationService.isCreditInterestFeasible(null))
        .isInstanceOf(LoanInterestEvaluationException.class);
  }

  // --- Installment Count Tests ---

  @Test
  void shouldAcceptValidInstallmentCount() {
    assertDoesNotThrow(() -> loanEvaluationService.isInstallmentFeasible(12));
  }

  @Test
  void shouldThrowForInvalidInstallmentCount() {
    assertThatThrownBy(() -> loanEvaluationService.isInstallmentFeasible(10))
        .isInstanceOf(LoanInstallmentEvaluationException.class)
        .hasMessageContaining("10");
  }
}
