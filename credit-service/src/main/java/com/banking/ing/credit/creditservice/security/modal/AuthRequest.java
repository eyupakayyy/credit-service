package com.banking.ing.credit.creditservice.security.modal;

import com.banking.ing.credit.creditservice.common.modal.base.BaseModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthRequest extends BaseModel {

  private String username;
  private String password;

}

