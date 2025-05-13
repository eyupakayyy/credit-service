package com.banking.ing.credit.creditservice.user.initial;

import static com.banking.ing.credit.creditservice.user.enums.UserRoleType.ADMIN;
import static com.banking.ing.credit.creditservice.user.enums.UserRoleType.CUSTOMER;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.banking.ing.credit.creditservice.common.entity.base.BaseEntity;
import com.banking.ing.credit.creditservice.common.initial.AbstractInitialData;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import com.banking.ing.credit.creditservice.user.enums.UserRoleType;
import com.banking.ing.credit.creditservice.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(value = 5)
@RequiredArgsConstructor
public class UserEntityInitialData extends AbstractInitialData implements ApplicationListener<ApplicationReadyEvent> {

  private final UserService service;

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    startProcess();
    log.info(String.format("%s initial users persisted!", service.findAll().size()));
  }

  @Override
  protected Boolean isExistData() {
    return !isEmpty(service.findAll());
  }

  @Override
  protected BaseEntity generateEntity(Object... args) {
    return UserEntity.builder()
        .username((String) args[0])
        .password((String) args[1])
        .role((UserRoleType) args[2])
        .build();
  }

  @Override
  protected void persistOperations() {
    final UserEntity employee1 = (UserEntity) generateEntity("employee1", "employee1_pass", ADMIN);
    final UserEntity employee2 = (UserEntity) generateEntity("employee2", "employee2_pass", ADMIN);
    final UserEntity customer1 = (UserEntity) generateEntity("customer1", "customer1_pass", CUSTOMER);
    final UserEntity customer2 = (UserEntity) generateEntity("customer2", "customer2_pass", CUSTOMER);
    final UserEntity customer3 = (UserEntity) generateEntity("customer3", "customer3_pass", CUSTOMER);
    for (UserEntity entity : List.of(employee1, employee2, customer1, customer2, customer3)) {
      service.create(entity);
    }
  }
}
