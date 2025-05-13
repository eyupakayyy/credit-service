package com.banking.ing.credit.creditservice.engine.service.impl;

import static com.banking.ing.credit.creditservice.common.util.FieldValueCheckUtil.isNull;

import com.banking.ing.credit.creditservice.engine.exception.LoanInstallmentEvaluationException;
import com.banking.ing.credit.creditservice.engine.exception.LoanInterestEvaluationException;
import com.banking.ing.credit.creditservice.engine.service.LoanEvaluationService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanEvaluationServiceImpl implements LoanEvaluationService {

  private static final List<Integer> ACCEPTED_INSTALLMENT_COUNTS = List.of(6, 9, 12, 24);

  private static final BigDecimal ACCEPTED_MINIMUM_INTEREST_RATE = BigDecimal.valueOf(0.1);
  private static final BigDecimal ACCEPTED_MAXIMUM_INTEREST_RATE = BigDecimal.valueOf(0.5);

  @Override
  public void isCreditInterestFeasible(final BigDecimal interestRate) {
    if (!isNull(interestRate) && (
        interestRate.compareTo(ACCEPTED_MINIMUM_INTEREST_RATE) >= 0
            && interestRate.compareTo(ACCEPTED_MAXIMUM_INTEREST_RATE) <= 0
    )) {
      return;
    }

    throw new LoanInterestEvaluationException(interestRate);
  }

  @Override
  public void isInstallmentFeasible(final int installment) {
    if (ACCEPTED_INSTALLMENT_COUNTS.contains(installment)) {
      return;
    }
    throw new LoanInstallmentEvaluationException(installment);
  }

}
