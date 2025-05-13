package com.banking.ing.credit.creditservice.credit.modal;

import com.banking.ing.credit.creditservice.common.modal.base.BaseModel;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerLoanListRequest extends BaseModel {

  private Long customerId;
  private BigDecimal amount;
  private int installmentCount;

}

