package com.banking.ing.credit.creditservice.credit.service;

import com.banking.ing.credit.creditservice.credit.entity.CustomerCreditEntity;
import java.util.List;

public interface CustomerCreditService {

  CustomerCreditEntity create(final CustomerCreditEntity request);

  CustomerCreditEntity fetch(final Long id);

  CustomerCreditEntity fetchByCustomerId(final Long customerId);

  List<CustomerCreditEntity> findAll();

  CustomerCreditEntity update(final Long customerId, final CustomerCreditEntity request);

  void delete(final Long id);

  void deleteByCustomerId(final Long customerId);

}
