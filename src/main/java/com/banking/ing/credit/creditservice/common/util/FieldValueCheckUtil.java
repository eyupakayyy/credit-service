package com.banking.ing.credit.creditservice.common.util;

import com.banking.ing.credit.creditservice.common.util.base.Utility;

public final class FieldValueCheckUtil extends Utility {

  public static boolean isNotNull(final Object value) {
    return value != null;
  }

  public static boolean isNull(final Object value) {
    return value == null;
  }

}
