package com.banking.ing.credit.creditservice.credit.service.impl;

import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import com.banking.ing.credit.creditservice.credit.exception.LoanInstallmentNotFoundException;
import com.banking.ing.credit.creditservice.credit.respository.LoanInstallmentRepository;
import com.banking.ing.credit.creditservice.credit.service.LoanInstallmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanInstallmentServiceImpl implements LoanInstallmentService {

  private final LoanInstallmentRepository loanInstallmentRepository;

  @Override
  public List<LoanInstallmentEntity> findAll() {
    return loanInstallmentRepository.findAll();
  }

  @Override
  public List<LoanInstallmentEntity> findAllByCustomerId(final Long customerId) {
    return loanInstallmentRepository.findAllByLoan_Customer_Id(customerId);
  }

  @Override
  public List<LoanInstallmentEntity> findAllByLoanId(final Long loanId) {
    return loanInstallmentRepository.findAllByLoanId(loanId);
  }

  @Override
  public LoanInstallmentEntity findById(final Long id) {
    return loanInstallmentRepository.findById(id)
        .orElseThrow(() -> new LoanInstallmentNotFoundException(id));
  }

  @Override
  public LoanInstallmentEntity update(final Long id, final LoanInstallmentEntity entity) {
    LoanInstallmentEntity entry = findById(id);
    entry.setPaid(entry.isPaid());
    entry.setPaidAmount(entry.getPaidAmount());
    return loanInstallmentRepository.save(entry);
  }

  @Override
  public List<LoanInstallmentEntity> updateAll(final List<LoanInstallmentEntity> entities) {
    return entities.stream()
        .map(entity -> {
          LoanInstallmentEntity entry = findById(entity.getId());
          entry.setPaid(entity.isPaid());
          entry.setPaidAmount(entity.getPaidAmount());
          entry.setPaymentDate(entity.getPaymentDate());
          return loanInstallmentRepository.save(entry);
        })
        .toList();
  }

  @Override
  public LoanInstallmentEntity create(final LoanInstallmentEntity entity) {
    return loanInstallmentRepository.save(entity);
  }

  @Override
  public List<LoanInstallmentEntity> create(final List<LoanInstallmentEntity> entities) {
    return loanInstallmentRepository.saveAll(entities);
  }

}
