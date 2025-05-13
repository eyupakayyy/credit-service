package com.banking.ing.credit.creditservice.engine.util;

import static com.banking.ing.credit.creditservice.common.util.FieldValueCheckUtil.isNull;

import com.banking.ing.credit.creditservice.common.util.base.Utility;
import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentRequest;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class LoanUtil extends Utility {

  private static final Double DEFAULT_DISCOUNT_OR_PENALTY_RATE = 0.001;

  public static boolean isLoanRequestFeasible(final CustomerLoanCreateRequest request) {
    if (isNull(request) || isNull(request.getAmount()) || isNull(request.getCustomerId())) {
      throw new UnsupportedOperationException("Missing Fields!");
    }
    return (request.getAmount().compareTo(BigDecimal.ZERO) > 0);
  }

  public static boolean isPaymentOfLoanInstallmentRequestFeasible(final CustomerLoanPaymentRequest request) {
    if (isNull(request) || isNull(request.getAmount()) || isNull(request.getCustomerId()) || isNull(request.getLoanId())) {
      throw new UnsupportedOperationException("Missing Fields!");
    }
    return (request.getAmount().compareTo(BigDecimal.ZERO) > 0);
  }

  public static LoanEntity createLoan(final UserEntity customer, final CustomerLoanCreateRequest request) {
    BigDecimal totalAmount = request.getAmount().multiply(BigDecimal.ONE.add(request.getInterestRate()));
    return LoanEntity.builder()
        .customer(customer)
        .loanAmount(totalAmount)
        .numberOfInstallments(request.getInstallmentCount())
        .build();
  }

  public static List<LoanInstallmentEntity> fetchSortedUnPaidLoanInstallments(final List<LoanInstallmentEntity> installments) {
    return installments.stream()
        .filter(i -> !i.isPaid())
        .sorted(Comparator.comparing(LoanInstallmentEntity::getDueDate))
        .toList();
  }

  public static List<LoanInstallmentEntity> createInstallments(final LoanEntity loan) {
    List<LoanInstallmentEntity> installments = new ArrayList<>();
    BigDecimal installmentAmount = loan.getLoanAmount().divide(BigDecimal.valueOf(loan.getNumberOfInstallments()), 2, RoundingMode.HALF_UP);
    LocalDate firstDueDate = YearMonth.now().plusMonths(1).atDay(1);
    for (int i = 0; i < loan.getNumberOfInstallments(); i++) {
      LoanInstallmentEntity installment = LoanInstallmentEntity.builder()
          .loan(loan)
          .amount(installmentAmount)
          .paidAmount(BigDecimal.ZERO)
          .dueDate(firstDueDate.plusMonths(i))
          .isPaid(false)
          .build();
      installments.add(installment);
    }
    return installments;
  }

  private static BigDecimal calculateDiscountedInstallmentPaymentAmount(final BigDecimal installmentAmount, final LocalDate dueDate,
      final LocalDate paymentDate) {
    long daysEarly = ChronoUnit.DAYS.between(paymentDate, dueDate);
    BigDecimal discountRate = BigDecimal.valueOf(DEFAULT_DISCOUNT_OR_PENALTY_RATE).multiply(BigDecimal.valueOf(daysEarly));
    BigDecimal discountAmount = installmentAmount.multiply(discountRate);
    return installmentAmount.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
  }

  private static BigDecimal calculatePenaltyInstallmentPaymentAmount(final BigDecimal installmentAmount, final LocalDate dueDate,
      final LocalDate paymentDate) {
    long daysLate = ChronoUnit.DAYS.between(dueDate, paymentDate);
    BigDecimal penaltyRate = BigDecimal.valueOf(DEFAULT_DISCOUNT_OR_PENALTY_RATE).multiply(BigDecimal.valueOf(daysLate));
    BigDecimal penaltyAmount = installmentAmount.multiply(penaltyRate);
    return installmentAmount.add(penaltyAmount).setScale(2, RoundingMode.HALF_UP);
  }

  public static BigDecimal calculateInstallmentPaymentAmount(final BigDecimal installmentAmount, final LocalDate dueDate) {
    LocalDate today = LocalDate.now();
    if (dueDate.isAfter(today)) {
      return calculateDiscountedInstallmentPaymentAmount(installmentAmount, dueDate, today);
    } else if (dueDate.isBefore(today)) {
      return calculatePenaltyInstallmentPaymentAmount(installmentAmount, dueDate, today);
    } else {
      return installmentAmount;
    }
  }

}
