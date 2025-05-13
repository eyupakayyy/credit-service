package com.banking.ing.credit.creditservice.credit.dto;

import com.banking.ing.credit.creditservice.common.modal.base.BaseModel;
import com.banking.ing.credit.creditservice.user.dto.UserDto;
import java.math.BigDecimal;
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
public class CustomerCreditDto extends BaseModel {

  private Long id;
  private UserDto customer;
  private BigDecimal creditLimit;
  private int creditScore;

}
