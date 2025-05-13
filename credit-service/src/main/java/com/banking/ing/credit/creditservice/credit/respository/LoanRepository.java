package com.banking.ing.credit.creditservice.credit.respository;

import com.banking.ing.credit.creditservice.common.repository.base.BaseRepository;
import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import java.util.List;

public interface LoanRepository extends BaseRepository<LoanEntity, Long> {

  boolean existsByCustomer(final LoanEntity entity);

  List<LoanEntity> findAllByCustomerId(final Long customerId);

}