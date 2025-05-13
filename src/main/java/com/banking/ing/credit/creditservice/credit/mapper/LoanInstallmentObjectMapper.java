package com.banking.ing.credit.creditservice.credit.mapper;

import static com.banking.ing.credit.creditservice.common.util.FieldValueCheckUtil.isNull;

import com.banking.ing.credit.creditservice.credit.dto.LoanInstallmentDto;
import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import com.banking.ing.credit.creditservice.credit.service.LoanService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanInstallmentObjectMapper {

  private final LoanObjectMapper loanObjectMapper;
  private final LoanService loanService;

  public LoanInstallmentDto toDto(final LoanInstallmentEntity entity) {
    if (isNull(entity)) {
      return null;
    }
    return LoanInstallmentDto.builder()
        .id(entity.getId())
        .loan(loanObjectMapper.toDto(entity.getLoan()))
        .amount(entity.getAmount())
        .dueDate(entity.getDueDate())
        .paymentDate(entity.getPaymentDate())
        .paidAmount(entity.getPaidAmount())
        .isPaid(entity.isPaid())
        .build();
  }

  public LoanInstallmentEntity toEntity(final LoanInstallmentDto dto) {
    if (isNull(dto)) {
      return null;
    }
    return LoanInstallmentEntity.builder()
        .loan(loanService.findById(dto.getLoan().getId()))
        .amount(dto.getAmount())
        .dueDate(dto.getDueDate())
        .paymentDate(dto.getPaymentDate())
        .paidAmount(dto.getPaidAmount())
        .isPaid(dto.isPaid())
        .build();
  }

  public List<LoanInstallmentDto> toDtoList(final List<LoanInstallmentEntity> entities) {
    if (isNull(entities)) {
      return Collections.emptyList();
    }
    return entities.stream()
        .map(this::toDto)
        .toList();
  }

  public List<LoanInstallmentEntity> toEntityList(final List<LoanInstallmentDto> dtos) {
    if (isNull(dtos)) {
      return Collections.emptyList();
    }
    return dtos.stream()
        .map(this::toEntity)
        .toList();
  }
}

