package com.banking.ing.credit.creditservice.credit.rest.v1;

import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_CREATED_WITH_PARAMETER;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_DELETED_WITH_PARAMETER;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_UPDATED_WITH_PARAMETER;
import static java.lang.String.format;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.credit.api.CustomerCreditApi;
import com.banking.ing.credit.creditservice.credit.dto.CustomerCreditDto;
import com.banking.ing.credit.creditservice.credit.entity.CustomerCreditEntity;
import com.banking.ing.credit.creditservice.credit.mapper.CustomerCreditObjectMapper;
import com.banking.ing.credit.creditservice.credit.service.CustomerCreditService;
import com.banking.ing.credit.creditservice.user.rest.BaseController;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerCreditController extends BaseController implements CustomerCreditApi {

  private static final String RESOURCE_TYPE_USER = "CustomerCredit";

  private final CustomerCreditService service;
  private final CustomerCreditObjectMapper mapper;

  @Override
  public ResponseEntity<ApiResponse<CustomerCreditDto>> create(final CustomerCreditDto dto) {
    CustomerCreditEntity entity = service.create(mapper.toEntity(dto));
    return ResponseEntity.created(URI.create(String.valueOf(entity.getId())))
        .body(
            ApiResponse.<CustomerCreditDto>builder()
                .success(true)
                .data(mapper.toDto(entity))
                .message(format(MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_CREATED_WITH_PARAMETER, RESOURCE_TYPE_USER))
                .build());
  }

  @Override
  public ResponseEntity<ApiResponse<CustomerCreditDto>> fetchByCustomerId(final Long customerId) {
    CustomerCreditEntity entity = service.fetchByCustomerId(customerId);
    return ResponseEntity.ok(ApiResponse.<CustomerCreditDto>builder()
        .success(true)
        .data(mapper.toDto(entity))
        .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, RESOURCE_TYPE_USER, customerId))
        .build());
  }

  @Override
  public ResponseEntity<ApiResponse<CustomerCreditDto>> fetch(final Long id) {
    CustomerCreditEntity entity = service.fetch(id);
    return ResponseEntity.ok(ApiResponse.<CustomerCreditDto>builder()
        .success(true)
        .data(mapper.toDto(entity))
        .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, RESOURCE_TYPE_USER, id))
        .build());
  }

  @Override
  public ResponseEntity<ApiResponse<List<CustomerCreditDto>>> fetchAll() {
    List<CustomerCreditEntity> entities = service.findAll();
    return ResponseEntity.ok(
        ApiResponse.<List<CustomerCreditDto>>builder()
            .success(true)
            .data(mapper.toDtoList(entities))
            .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, entities.size(), RESOURCE_TYPE_USER))
            .build()
    );
  }

  @Override
  public ResponseEntity<ApiResponse<CustomerCreditDto>> update(final Long customerId, final CustomerCreditDto request) {
    CustomerCreditEntity entity = service.update(customerId, mapper.toEntity(request));
    return ResponseEntity.ok(ApiResponse.<CustomerCreditDto>builder().success(true)
        .data(mapper.toDto(entity))
        .message(format(MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_UPDATED_WITH_PARAMETER, RESOURCE_TYPE_USER))
        .build());
  }

  @Override
  public ResponseEntity<ApiResponse<Void>> deleteByCustomerId(final Long customerId) {
    service.deleteByCustomerId(customerId);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .success(true)
            .message(String.format(MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_DELETED_WITH_PARAMETER, RESOURCE_TYPE_USER))
            .build()
    );
  }

  @Override
  public ResponseEntity<ApiResponse<Void>> delete(final Long id) {
    service.delete(id);
    return ResponseEntity.ok(
        ApiResponse.<Void>builder()
            .success(true)
            .message(String.format(MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_DELETED_WITH_PARAMETER, RESOURCE_TYPE_USER))
            .build()
    );
  }
}
