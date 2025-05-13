package com.banking.ing.credit.creditservice.credit.respository;

import com.banking.ing.credit.creditservice.common.repository.base.BaseRepository;
import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import java.util.List;

public interface LoanInstallmentRepository extends BaseRepository<LoanInstallmentEntity, Long> {

  List<LoanInstallmentEntity> findAllByLoanId(final Long loanId);

  List<LoanInstallmentEntity> findAllByLoan_Customer_Id(Long customerId);

}