package com.banking.ing.credit.creditservice.user.mapper;

import static com.banking.ing.credit.creditservice.common.util.FieldValueCheckUtil.isNull;

import com.banking.ing.credit.creditservice.user.dto.UserDto;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserObjectMapper {

  public UserDto toDto(final UserEntity entity) {
    if (isNull(entity)) {
      return null;
    }
    return UserDto.builder()
        .id(entity.getId())
        .username(entity.getUsername())
        .role(entity.getRole())
        .build();
  }

  public UserEntity toEntity(final UserDto dto) {
    if (isNull(dto)) {
      return null;
    }
    return UserEntity.builder()
        .username(dto.getUsername())
        .password(dto.getPassword())
        .role(dto.getRole())
        .build();
  }

  public List<UserDto> toDtoList(final List<UserEntity> entities) {
    if (isNull(entities)) {
      return Collections.emptyList();
    }
    return entities.stream()
        .map(this::toDto)
        .toList();
  }

  public List<UserEntity> toEntityList(final List<UserDto> dtos) {
    if (isNull(dtos)) {
      return Collections.emptyList();
    }
    return dtos.stream()
        .map(this::toEntity)
        .toList();
  }
}

