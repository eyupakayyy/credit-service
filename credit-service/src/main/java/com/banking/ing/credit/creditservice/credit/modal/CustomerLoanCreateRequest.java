package com.banking.ing.credit.creditservice.credit.modal;

import com.banking.ing.credit.creditservice.common.modal.base.BaseModel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerLoanCreateRequest extends BaseModel {

  @NotNull(message = "Customer ID must not be null")
  private Long customerId;
  @NotNull(message = "Amount must not be null")
  @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
  private BigDecimal amount;
  @NotNull(message = "Installment count must not be null")
  @Min(value = 1, message = "Installment count must be at least 1")
  private int installmentCount;
  @NotNull(message = "Interest rate must not be null")
  @DecimalMin(value = "0.0", message = "Interest rate must be positive or zero")
  private BigDecimal interestRate;

}

