package com.banking.ing.credit.creditservice.credit.service.impl;

import com.banking.ing.credit.creditservice.credit.entity.CustomerCreditEntity;
import com.banking.ing.credit.creditservice.credit.exception.CreditNotFoundException;
import com.banking.ing.credit.creditservice.credit.exception.CustomerCreditAlreadyExistsException;
import com.banking.ing.credit.creditservice.credit.respository.CustomerCreditRepository;
import com.banking.ing.credit.creditservice.credit.service.CustomerCreditService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerCreditServiceImpl implements CustomerCreditService {

  private final CustomerCreditRepository repository;

  @Override
  public CustomerCreditEntity fetch(final Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new CreditNotFoundException(id));
  }

  @Override
  public CustomerCreditEntity fetchByCustomerId(final Long customerId) {
    return repository.findByCustomerId(customerId)
        .orElseThrow(() -> new CreditNotFoundException(customerId));
  }

  @Override
  public List<CustomerCreditEntity> findAll() {
    return repository.findAll();
  }

  @Override
  public CustomerCreditEntity create(final CustomerCreditEntity entity) {
    if (repository.existsByCustomer(entity.getCustomer())) {
      throw new CustomerCreditAlreadyExistsException(entity.getCustomer().getId());
    }
    return repository.save(entity);
  }

  @Override
  public CustomerCreditEntity update(final Long id, final CustomerCreditEntity entity) {
    CustomerCreditEntity entry = fetch(id);
    entry.setCreditLimit(entity.getCreditLimit());
    entry.setCreditScore(entity.getCreditScore());
    return repository.save(entity);
  }

  @Override
  public void delete(final Long id) {
    CustomerCreditEntity entity = fetch(id);
    entity.setDeleted(true);
    repository.save(entity);
  }

  @Override
  public void deleteByCustomerId(final Long customerId) {
    CustomerCreditEntity entity = fetchByCustomerId(customerId);
    entity.setDeleted(true);
    repository.save(entity);
  }
}
