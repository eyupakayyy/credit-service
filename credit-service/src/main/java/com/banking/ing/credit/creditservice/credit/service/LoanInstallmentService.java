package com.banking.ing.credit.creditservice.credit.service;

import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import java.util.List;

public interface LoanInstallmentService {

  List<LoanInstallmentEntity> findAll();

  List<LoanInstallmentEntity> findAllByCustomerId(final Long customerId);

  List<LoanInstallmentEntity> findAllByLoanId(final Long loanId);

  LoanInstallmentEntity findById(final Long id);

  LoanInstallmentEntity update(final Long id, final LoanInstallmentEntity entity);

  List<LoanInstallmentEntity> updateAll(final List<LoanInstallmentEntity> entities);

  LoanInstallmentEntity create(final LoanInstallmentEntity entity);

  List<LoanInstallmentEntity> create(final List<LoanInstallmentEntity> entity);

}
