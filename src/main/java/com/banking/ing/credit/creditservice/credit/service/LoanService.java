package com.banking.ing.credit.creditservice.credit.service;

import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import java.util.List;

public interface LoanService {

  List<LoanEntity> findAll();

  List<LoanEntity> findAllByCustomerId(final Long customerId);

  LoanEntity findById(final Long id);

  LoanEntity create(final LoanEntity entity);

  void delete(final Long id);

}
