package com.banking.ing.credit.creditservice.credit.modal;

import com.banking.ing.credit.creditservice.common.modal.base.BaseModel;
import com.banking.ing.credit.creditservice.credit.dto.LoanDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerLoanListResponse extends BaseModel {

  private Long customerId;
  private List<LoanDto> loans;

}

