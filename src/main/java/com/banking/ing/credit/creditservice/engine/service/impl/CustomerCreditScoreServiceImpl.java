package com.banking.ing.credit.creditservice.engine.service.impl;

import com.banking.ing.credit.creditservice.credit.entity.CustomerCreditEntity;
import com.banking.ing.credit.creditservice.credit.service.CustomerCreditService;
import com.banking.ing.credit.creditservice.engine.service.CustomerCreditScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerCreditScoreServiceImpl implements CustomerCreditScoreService {

  private static final int DEFAULT_CREDIT_SCORE_LIMIT = 1500;
  private final CustomerCreditService customerCreditService;

  @Override
  public boolean customerCreditScoreEvaluation(final Long customerId) {
    CustomerCreditEntity entity = customerCreditService.fetchByCustomerId(customerId);
    return entity.getCreditScore() > DEFAULT_CREDIT_SCORE_LIMIT;
  }
}
