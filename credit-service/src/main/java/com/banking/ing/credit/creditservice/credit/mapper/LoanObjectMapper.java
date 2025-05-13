package com.banking.ing.credit.creditservice.credit.mapper;

import static com.banking.ing.credit.creditservice.common.util.FieldValueCheckUtil.isNull;

import com.banking.ing.credit.creditservice.credit.dto.LoanDto;
import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import com.banking.ing.credit.creditservice.user.mapper.UserObjectMapper;
import com.banking.ing.credit.creditservice.user.service.UserService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanObjectMapper {

  private final UserService userService;
  private final UserObjectMapper userObjectMapper;

  public LoanDto toDto(final LoanEntity entity) {
    if (isNull(entity)) {
      return null;
    }
    return LoanDto.builder()
        .id(entity.getId())
        .loanAmount(entity.getLoanAmount())
        .numberOfInstallments(entity.getNumberOfInstallments())
        .customer(userObjectMapper.toDto(entity.getCustomer()))
        .build();
  }

  public LoanEntity toEntity(final LoanDto dto) {
    if (isNull(dto)) {
      return null;
    }
    return LoanEntity.builder()
        .loanAmount(dto.getLoanAmount())
        .numberOfInstallments(dto.getNumberOfInstallments())
        .customer(userService.fetchById(dto.getCustomer().getId()))
        .build();
  }

  public List<LoanDto> toDtoList(final List<LoanEntity> entities) {
    if (isNull(entities)) {
      return Collections.emptyList();
    }
    return entities.stream()
        .map(this::toDto)
        .toList();
  }

  public List<LoanEntity> toEntityList(final List<LoanDto> dtos) {
    if (isNull(dtos)) {
      return Collections.emptyList();
    }
    return dtos.stream()
        .map(this::toEntity)
        .toList();
  }
}

