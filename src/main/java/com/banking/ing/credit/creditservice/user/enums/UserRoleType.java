package com.banking.ing.credit.creditservice.user.enums;

import lombok.Getter;

@Getter
public enum UserRoleType {

  ADMIN("ROLE_ADMIN", "Admin"),
  CUSTOMER("ROLE_CUSTOMER", "Customer");

  private final String code;
  private final String displayName;

  UserRoleType(String code, String displayName) {
    this.code = code;
    this.displayName = displayName;
  }

}
