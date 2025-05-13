package com.banking.ing.credit.creditservice.credit.mapper;

import static com.banking.ing.credit.creditservice.common.util.FieldValueCheckUtil.isNull;

import com.banking.ing.credit.creditservice.credit.dto.CustomerCreditDto;
import com.banking.ing.credit.creditservice.credit.entity.CustomerCreditEntity;
import com.banking.ing.credit.creditservice.user.mapper.UserObjectMapper;
import com.banking.ing.credit.creditservice.user.service.UserService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerCreditObjectMapper {

  private final UserService userService;
  private final UserObjectMapper userObjectMapper;

  public CustomerCreditDto toDto(final CustomerCreditEntity entity) {
    if (isNull(entity)) {
      return null;
    }
    return CustomerCreditDto.builder()
        .id(entity.getId())
        .customer(userObjectMapper.toDto(entity.getCustomer()))
        .creditLimit(entity.getCreditLimit())
        .creditScore(entity.getCreditScore())
        .build();
  }

  public CustomerCreditEntity toEntity(final CustomerCreditDto dto) {
    if (isNull(dto)) {
      return null;
    }
    return CustomerCreditEntity.builder()
        .customer(userService.fetchById(dto.getCustomer().getId()))
        .creditLimit(dto.getCreditLimit())
        .creditScore(dto.getCreditScore())
        .build();
  }

  public List<CustomerCreditDto> toDtoList(final List<CustomerCreditEntity> entities) {
    if (isNull(entities)) {
      return Collections.emptyList();
    }
    return entities.stream()
        .map(this::toDto)
        .toList();
  }

  public List<CustomerCreditEntity> toEntityList(final List<CustomerCreditDto> dtos) {
    if (isNull(dtos)) {
      return Collections.emptyList();
    }
    return dtos.stream()
        .map(this::toEntity)
        .toList();
  }
}

