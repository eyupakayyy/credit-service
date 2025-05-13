package com.banking.ing.credit.creditservice.credit.rest.v1;

import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_CREATED_WITH_PARAMETER;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_UPDATED_WITH_PARAMETER;
import static java.lang.String.format;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.credit.api.LoanInstallmentApi;
import com.banking.ing.credit.creditservice.credit.dto.LoanInstallmentDto;
import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import com.banking.ing.credit.creditservice.credit.mapper.LoanInstallmentObjectMapper;
import com.banking.ing.credit.creditservice.credit.service.LoanInstallmentService;
import com.banking.ing.credit.creditservice.user.rest.BaseController;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoanInstallmentController extends BaseController implements LoanInstallmentApi {

  private static final String RESOURCE_TYPE_USER = "LoanInstallment";

  private final LoanInstallmentService service;
  private final LoanInstallmentObjectMapper mapper;

  @Override
  public ResponseEntity<ApiResponse<LoanInstallmentDto>> create(final LoanInstallmentDto dto) {
    LoanInstallmentEntity entity = service.create(mapper.toEntity(dto));
    return ResponseEntity.created(URI.create(String.valueOf(entity.getId())))
        .body(
            ApiResponse.<LoanInstallmentDto>builder()
                .success(true)
                .data(mapper.toDto(entity))
                .message(format(MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_CREATED_WITH_PARAMETER, RESOURCE_TYPE_USER))
                .build());
  }

  @Override
  public ResponseEntity<ApiResponse<LoanInstallmentDto>> update(final Long id, final LoanInstallmentDto dto) {
    LoanInstallmentEntity entity = service.update(id, mapper.toEntity(dto));
    return ResponseEntity.ok(ApiResponse.<LoanInstallmentDto>builder().success(true)
        .data(mapper.toDto(entity))
        .message(format(MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_UPDATED_WITH_PARAMETER, RESOURCE_TYPE_USER))
        .build());
  }

  @Override
  public ResponseEntity<ApiResponse<LoanInstallmentDto>> fetch(final Long id) {
    LoanInstallmentEntity entity = service.findById(id);
    return ResponseEntity.ok(ApiResponse.<LoanInstallmentDto>builder()
        .success(true)
        .data(mapper.toDto(entity))
        .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, RESOURCE_TYPE_USER, id))
        .build());
  }

  @Override
  public ResponseEntity<ApiResponse<List<LoanInstallmentDto>>> fetchAll() {
    List<LoanInstallmentEntity> entities = service.findAll();
    return ResponseEntity.ok(
        ApiResponse.<List<LoanInstallmentDto>>builder()
            .success(true)
            .data(mapper.toDtoList(entities))
            .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, entities.size(), RESOURCE_TYPE_USER))
            .build()
    );
  }

  @Override
  public ResponseEntity<ApiResponse<List<LoanInstallmentDto>>> fetchByLoanId(final Long id) {
    List<LoanInstallmentEntity> entities = service.findAllByLoanId(id);
    return ResponseEntity.ok(
        ApiResponse.<List<LoanInstallmentDto>>builder()
            .success(true)
            .data(mapper.toDtoList(entities))
            .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, entities.size(), RESOURCE_TYPE_USER))
            .build()
    );
  }
}
