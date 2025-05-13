package com.banking.ing.credit.creditservice.engine.service.impl;

import com.banking.ing.credit.creditservice.credit.entity.CustomerCreditEntity;
import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import com.banking.ing.credit.creditservice.credit.service.CustomerCreditService;
import com.banking.ing.credit.creditservice.credit.service.LoanInstallmentService;
import com.banking.ing.credit.creditservice.credit.service.LoanService;
import com.banking.ing.credit.creditservice.engine.service.CustomerCreditEvaluationService;
import com.banking.ing.credit.creditservice.engine.service.CustomerCreditScoreService;
import com.banking.ing.credit.creditservice.engine.service.CustomerFraudService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerCreditEvaluationServiceImpl implements CustomerCreditEvaluationService {

  private final CustomerCreditService customerCreditService;
  private final CustomerFraudService fraudService;
  private final CustomerCreditScoreService creditScoreService;
  private final LoanService loanService;
  private final LoanInstallmentService installmentService;

  @Override
  public boolean isCustomerEligible(final Long customerId, final BigDecimal amount) {
    return fraudService.customerFraudEvaluation(customerId)
        && creditScoreService.customerCreditScoreEvaluation(customerId);
  }

  @Override
  public boolean evaluateCustomerLoanLimit(final Long customerId, final BigDecimal amount) {
    CustomerCreditEntity entry = customerCreditService.fetchByCustomerId(customerId);
    List<LoanInstallmentEntity> installments = installmentService.findAllByCustomerId(customerId);
    List<LoanEntity> loans = loanService.findAllByCustomerId(customerId);
    BigDecimal totalPaidInstallmentsAmount = totalPaidInstallments(installments);
    BigDecimal totalUsedLoanAmount = totalUsedLoans(loans);
    BigDecimal usableCreditLimit = evaluateCustomerCreditLimitWithUsedLoanAndPaidInstallments(entry.getCreditLimit(), totalUsedLoanAmount,
        totalPaidInstallmentsAmount);

    return (usableCreditLimit.compareTo(BigDecimal.ZERO) > 0);
  }

  private BigDecimal evaluateCustomerCreditLimitWithUsedLoanAndPaidInstallments(final BigDecimal creditLimit, final BigDecimal usedLoansAmount,
      BigDecimal paidInstallmentsAmount) {
    return creditLimit
        .subtract(usedLoansAmount != null ? usedLoansAmount : BigDecimal.ZERO)
        .add(paidInstallmentsAmount != null ? paidInstallmentsAmount : BigDecimal.ZERO);
  }

  private BigDecimal totalPaidInstallments(final List<LoanInstallmentEntity> installments) {
    return installments.stream()
        .filter(LoanInstallmentEntity::isPaid)
        .map(LoanInstallmentEntity::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal totalUsedLoans(final List<LoanEntity> loans) {
    return loans.stream()
        .map(LoanEntity::getLoanAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
