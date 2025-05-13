package com.banking.ing.credit.creditservice.engine.service;

import java.math.BigDecimal;

public interface LoanEvaluationService {

  void isInstallmentFeasible(final int installment);

  void isCreditInterestFeasible(final BigDecimal interestRate);

}
