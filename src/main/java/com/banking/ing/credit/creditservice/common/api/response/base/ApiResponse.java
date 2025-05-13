package com.banking.ing.credit.creditservice.common.api.response.base;

import com.banking.ing.credit.creditservice.common.modal.base.AbstractBaseModel;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<PAYLOAD> extends AbstractBaseModel implements Serializable {

  private boolean success;
  private String message;
  private PAYLOAD data;

}
