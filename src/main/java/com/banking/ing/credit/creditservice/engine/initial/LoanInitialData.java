package com.banking.ing.credit.creditservice.engine.initial;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.banking.ing.credit.creditservice.common.entity.base.BaseEntity;
import com.banking.ing.credit.creditservice.common.initial.AbstractInitialData;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateRequest;
import com.banking.ing.credit.creditservice.credit.service.LoanInstallmentService;
import com.banking.ing.credit.creditservice.engine.service.CreditService;
import com.banking.ing.credit.creditservice.user.service.UserService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(value = 9)
@RequiredArgsConstructor
public class LoanInitialData extends AbstractInitialData implements ApplicationListener<ApplicationReadyEvent> {

  private final LoanInstallmentService loanInstallmentService;
  private final CreditService creditService;
  private final UserService userService;

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    startProcess();
    log.info(String.format("%s initial loan installments persisted!", loanInstallmentService.findAll().size()));
  }

  @Override
  protected Boolean isExistData() {
    return !isEmpty(loanInstallmentService.findAll());
  }

  @Override
  protected BaseEntity generateEntity(Object... args) {
    return null;
  }

  @Override
  protected void persistOperations() {
    CustomerLoanCreateRequest customerLoan1 = CustomerLoanCreateRequest.builder()
        .customerId(userService.findByUsername("customer1").getId())
        .amount(BigDecimal.valueOf(123000))
        .installmentCount(9)
        .interestRate(BigDecimal.valueOf(0.3))
        .build();

    CustomerLoanCreateRequest customerLoan2 = CustomerLoanCreateRequest.builder()
        .customerId(userService.findByUsername("customer1").getId())
        .amount(BigDecimal.valueOf(250000))
        .installmentCount(24)
        .interestRate(BigDecimal.valueOf(0.4))
        .build();

    CustomerLoanCreateRequest customerLoan3 = CustomerLoanCreateRequest.builder()
        .customerId(userService.findByUsername("customer2").getId())
        .amount(BigDecimal.valueOf(85000))
        .installmentCount(6)
        .interestRate(BigDecimal.valueOf(0.2))
        .build();
    creditService.createLoanWithInstallments(customerLoan1.getCustomerId(), customerLoan1);
    creditService.createLoanWithInstallments(customerLoan2.getCustomerId(), customerLoan2);
    creditService.createLoanWithInstallments(customerLoan2.getCustomerId(), customerLoan3);
  }
}
