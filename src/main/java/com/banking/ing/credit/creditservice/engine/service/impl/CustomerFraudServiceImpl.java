package com.banking.ing.credit.creditservice.engine.service.impl;

import com.banking.ing.credit.creditservice.credit.exception.CreditNotFoundException;
import com.banking.ing.credit.creditservice.credit.service.CustomerCreditService;
import com.banking.ing.credit.creditservice.engine.service.CustomerFraudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerFraudServiceImpl implements CustomerFraudService {

  private final CustomerCreditService customerCreditService;

  @Override
  public boolean customerFraudEvaluation(final Long customerId) {
    try {
      customerCreditService.fetchByCustomerId(customerId);
      return true;
    } catch (final CreditNotFoundException ex) {
      return false;
    }
  }

}
