package com.banking.ing.credit.creditservice.security.modal;

import com.banking.ing.credit.creditservice.common.modal.base.BaseModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse extends BaseModel {

  private final String token;

}