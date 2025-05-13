package com.banking.ing.credit.creditservice.user.rest.v1;

import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_CREATED_WITH_PARAMETER;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_DELETED_WITH_PARAMETER;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_UPDATED_WITH_PARAMETER;
import static java.lang.String.format;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.user.api.UserApi;
import com.banking.ing.credit.creditservice.user.dto.UserDto;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import com.banking.ing.credit.creditservice.user.exception.UserNotFoundException;
import com.banking.ing.credit.creditservice.user.mapper.UserObjectMapper;
import com.banking.ing.credit.creditservice.user.rest.BaseController;
import com.banking.ing.credit.creditservice.user.service.UserService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController implements UserApi {

  private static final String RESOURCE_TYPE_USER = "User";

  private final UserService service;
  private final UserObjectMapper mapper;

  @Override
  public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
    List<UserEntity> entities = service.findAll();
    return ResponseEntity.ok(
        ApiResponse.<List<UserDto>>builder()
            .success(true)
            .data(mapper.toDtoList(entities))
            .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, entities.size(), RESOURCE_TYPE_USER))
            .build()
    );
  }

  @Override
  public ResponseEntity<ApiResponse<UserDto>> getUserById(final Long id) {
    UserEntity entity = service.findById(id)
        .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    return ResponseEntity.ok(ApiResponse.<UserDto>builder()
        .success(true)
        .data(mapper.toDto(entity))
        .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, RESOURCE_TYPE_USER, id))
        .build());
  }

  @Override
  public ResponseEntity<ApiResponse<UserDto>> getUserByUsername(final String username) {
    UserEntity entity = service.findByUsername(username);
    return ResponseEntity.ok(ApiResponse.<UserDto>builder()
        .success(true)
        .data(mapper.toDto(entity))
        .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, RESOURCE_TYPE_USER, username))
        .build());
  }

  @Override
  public ResponseEntity<ApiResponse<UserDto>> createUser(final UserDto user) {
    UserEntity entity = service.create(mapper.toEntity(user));
    return ResponseEntity.created(URI.create(entity.getUsername()))
        .body(
            ApiResponse.<UserDto>builder()
                .success(true)
                .data(mapper.toDto(entity))
                .message(format(MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_CREATED_WITH_PARAMETER, RESOURCE_TYPE_USER))
                .build());
  }

  @Override
  public ResponseEntity<ApiResponse<UserDto>> updateUser(final Long id, final UserDto user) {
    UserEntity entity = service.update(id, mapper.toEntity(user));
    return ResponseEntity.ok(ApiResponse.<UserDto>builder().success(true)
        .data(mapper.toDto(entity))
        .message(format(MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_UPDATED_WITH_PARAMETER, RESOURCE_TYPE_USER))
        .build());
  }

  @Override
  public ResponseEntity<ApiResponse<Void>> deleteUser(final Long id) {
    service.delete(id);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .success(true)
            .message(String.format(MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_DELETED_WITH_PARAMETER, RESOURCE_TYPE_USER))
            .build()
    );
  }

}
