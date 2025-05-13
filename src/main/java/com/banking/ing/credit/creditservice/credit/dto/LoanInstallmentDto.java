package com.banking.ing.credit.creditservice.credit.dto;

import com.banking.ing.credit.creditservice.common.modal.base.BaseModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanInstallmentDto extends BaseModel {

  private Long id;
  private LoanDto loan;
  private BigDecimal amount;
  private BigDecimal paidAmount;
  private LocalDate dueDate;
  private LocalDate paymentDate;
  private boolean isPaid;

}
