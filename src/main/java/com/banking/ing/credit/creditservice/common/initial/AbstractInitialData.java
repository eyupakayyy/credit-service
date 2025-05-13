package com.banking.ing.credit.creditservice.common.initial;

import com.banking.ing.credit.creditservice.common.entity.base.BaseEntity;

public abstract class AbstractInitialData {

  protected abstract Boolean isExistData();

  protected abstract BaseEntity generateEntity(Object... args);

  protected abstract void persistOperations();

  protected void startProcess() {
    if (!isExistData()) {
      persistOperations();
    }
  }
}
