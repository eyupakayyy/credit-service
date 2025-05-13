package com.banking.ing.credit.creditservice.engine.initial;

import static com.banking.ing.credit.creditservice.user.enums.UserRoleType.CUSTOMER;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.banking.ing.credit.creditservice.common.entity.base.BaseEntity;
import com.banking.ing.credit.creditservice.common.initial.AbstractInitialData;
import com.banking.ing.credit.creditservice.credit.entity.CustomerCreditEntity;
import com.banking.ing.credit.creditservice.credit.service.CustomerCreditService;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import com.banking.ing.credit.creditservice.user.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(value = 7)
@RequiredArgsConstructor
public class CustomerCreditInitialData extends AbstractInitialData implements ApplicationListener<ApplicationReadyEvent> {

  private final CustomerCreditService creditService;
  private final UserService userService;

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    startProcess();
    log.info(String.format("%s initial customer credits persisted!", creditService.findAll().size()));
  }

  @Override
  protected Boolean isExistData() {
    return !isEmpty(creditService.findAll());
  }

  @Override
  protected BaseEntity generateEntity(Object... args) {
    return CustomerCreditEntity.builder()
        .customer((UserEntity) args[0])
        .creditLimit((BigDecimal) args[1])
        .creditScore((int) args[2])
        .build();
  }

  @Override
  protected void persistOperations() {
    List<UserEntity> users = userService.findAll();
    for (UserEntity user : users) {
      if (user.getRole() == CUSTOMER) {
        final CustomerCreditEntity entity = (CustomerCreditEntity) generateEntity(user, BigDecimal.valueOf(900000), 1700);
        creditService.create(entity);
      }
    }
  }
}
