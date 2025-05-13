package com.banking.ing.credit.creditservice.engine.service;

import java.math.BigDecimal;

public interface CustomerCreditEvaluationService {

  boolean isCustomerEligible(final Long customerId, final BigDecimal amount);

  boolean evaluateCustomerLoanLimit(final Long customerId, final BigDecimal amount);

}
