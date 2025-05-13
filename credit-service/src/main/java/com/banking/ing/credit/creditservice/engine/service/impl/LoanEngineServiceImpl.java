package com.banking.ing.credit.creditservice.engine.service.impl;

import static com.banking.ing.credit.creditservice.common.util.FieldValueCheckUtil.isNull;
import static com.banking.ing.credit.creditservice.engine.util.LoanUtil.calculateInstallmentPaymentAmount;
import static com.banking.ing.credit.creditservice.engine.util.LoanUtil.createInstallments;
import static com.banking.ing.credit.creditservice.engine.util.LoanUtil.createLoan;
import static com.banking.ing.credit.creditservice.engine.util.LoanUtil.fetchSortedUnPaidLoanInstallments;

import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import com.banking.ing.credit.creditservice.credit.mapper.LoanInstallmentObjectMapper;
import com.banking.ing.credit.creditservice.credit.mapper.LoanObjectMapper;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentResponse;
import com.banking.ing.credit.creditservice.credit.service.LoanInstallmentService;
import com.banking.ing.credit.creditservice.credit.service.LoanService;
import com.banking.ing.credit.creditservice.engine.exception.CreditInstallmentPaymentEligibilityException;
import com.banking.ing.credit.creditservice.engine.service.LoanEngineService;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import com.banking.ing.credit.creditservice.user.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoanEngineServiceImpl implements LoanEngineService {

  private final UserService userService;
  private final LoanService loanService;
  private final LoanInstallmentService loanInstallmentService;
  private final LoanObjectMapper loanObjectMapper;
  private final LoanInstallmentObjectMapper loanInstallmentObjectMapper;

  @Override
  @Transactional
  public CustomerLoanCreateResponse initiateLoan(final CustomerLoanCreateRequest request) {
    UserEntity user = userService.fetchById(request.getCustomerId());
    LoanEntity loan = createLoan(user, request);
    List<LoanInstallmentEntity> installments = createInstallments(loan);
    LoanEntity persistedLoan = loanService.create(loan);
    installments.forEach(i -> i.setLoan(persistedLoan));
    List<LoanInstallmentEntity> persistedInstallments = loanInstallmentService.create(installments);
    return CustomerLoanCreateResponse.builder()
        .loanDto(loanObjectMapper.toDto(persistedLoan))
        .installmentDto(loanInstallmentObjectMapper.toDtoList(persistedInstallments))
        .customerId(loan.getCustomer().getId())
        .build();
  }

  @Override
  @Transactional
  public CustomerLoanPaymentResponse paymentProcess(final CustomerLoanPaymentRequest request) {
    List<LoanInstallmentEntity> installments = findPayableInstallments(request.getLoanId());
    List<LoanInstallmentEntity> paidInstallments = new ArrayList<>();
    BigDecimal availableBalance = request.getAmount();

    for (final LoanInstallmentEntity installment : installments) {
      final BigDecimal paymentAmount = calculateInstallmentPaymentAmount(installment.getAmount(), installment.getDueDate());
      if (availableBalance.compareTo(paymentAmount) >= 0) {
        availableBalance = availableBalance.subtract(paymentAmount);
        final LoanInstallmentEntity paidInstallment = processPayment(installment, paymentAmount);
        paidInstallments.add(paidInstallment);
      } else {
        break;
      }
    }

    List<LoanInstallmentEntity> result = loanInstallmentService.updateAll(paidInstallments);
    return CustomerLoanPaymentResponse.builder()
        .customerId(request.getCustomerId())
        .paidInstallments(loanInstallmentObjectMapper.toDtoList(result))
        .availableBalance(availableBalance)
        .build();
  }

  private LoanInstallmentEntity processPayment(final LoanInstallmentEntity installment, final BigDecimal paymentAmount) {
    installment.setPaid(true);
    installment.setPaidAmount(paymentAmount);
    installment.setPaymentDate(LocalDate.now());
    return installment;
  }

  private List<LoanInstallmentEntity> findPayableInstallments(final Long loanId) {
    List<LoanInstallmentEntity> installments = fetchSortedUnPaidLoanInstallments(loanInstallmentService.findAllByLoanId(loanId));
    if (isNull(installments) || installments.isEmpty()) {
      throw new CreditInstallmentPaymentEligibilityException("Cannot find payable installment for loan:" + loanId);
    }
    return installments;
  }

}
