package com.banking.ing.credit.creditservice.credit.service.impl;

import static java.lang.Boolean.TRUE;

import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import com.banking.ing.credit.creditservice.credit.exception.LoanNotFoundException;
import com.banking.ing.credit.creditservice.credit.respository.LoanRepository;
import com.banking.ing.credit.creditservice.credit.service.LoanService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

  private final LoanRepository loanRepository;

  @Override
  public List<LoanEntity> findAll() {
    return loanRepository.findAll();
  }

  @Override
  public List<LoanEntity> findAllByCustomerId(final Long customerId) {
    return loanRepository.findAllByCustomerId(customerId);
  }

  @Override
  public LoanEntity findById(final Long id) {
    return loanRepository.findById(id)
        .orElseThrow(() -> new LoanNotFoundException(id));
  }

  @Override
  public LoanEntity create(final LoanEntity entity) {
    return loanRepository.save(entity);
  }

  @Override
  public void delete(final Long id) {
    LoanEntity entry = findById(id);
    entry.setDeleted(TRUE);
    loanRepository.save(entry);

  }
}
