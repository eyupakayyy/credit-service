package com.banking.ing.credit.creditservice.user.dto;

import com.banking.ing.credit.creditservice.common.modal.base.BaseModel;
import com.banking.ing.credit.creditservice.user.enums.UserRoleType;
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
public class UserDto extends BaseModel {

  private long id;
  private String username;
  private String password;
  private UserRoleType role;

}
