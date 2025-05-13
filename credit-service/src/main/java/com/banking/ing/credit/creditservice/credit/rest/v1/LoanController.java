package com.banking.ing.credit.creditservice.credit.rest.v1;

import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_CREATED_WITH_PARAMETER;
import static com.banking.ing.credit.creditservice.common.constants.CommonApiMessages.MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_DELETED_WITH_PARAMETER;
import static java.lang.String.format;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.credit.api.LoanApi;
import com.banking.ing.credit.creditservice.credit.dto.LoanDto;
import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import com.banking.ing.credit.creditservice.credit.mapper.LoanObjectMapper;
import com.banking.ing.credit.creditservice.credit.service.LoanService;
import com.banking.ing.credit.creditservice.user.rest.BaseController;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoanController extends BaseController implements LoanApi {

  private static final String RESOURCE_TYPE_USER = "Loan";

  private final LoanService service;
  private final LoanObjectMapper mapper;

  @Override
  public ResponseEntity<ApiResponse<LoanDto>> create(final LoanDto dto) {
    LoanEntity entity = service.create(mapper.toEntity(dto));
    return ResponseEntity.created(URI.create(String.valueOf(entity.getId())))
        .body(
            ApiResponse.<LoanDto>builder()
                .success(true)
                .data(mapper.toDto(entity))
                .message(format(MESSAGE_PARAMETERIZED_RESOURCE_SUCCESSFULLY_CREATED_WITH_PARAMETER, RESOURCE_TYPE_USER))
                .build());
  }

  @Override
  public ResponseEntity<ApiResponse<LoanDto>> fetch(final Long id) {
    LoanEntity entity = service.findById(id);
    return ResponseEntity.ok(ApiResponse.<LoanDto>builder()
        .success(true)
        .data(mapper.toDto(entity))
        .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, RESOURCE_TYPE_USER, id))
        .build());
  }

  @Override
  public ResponseEntity<ApiResponse<List<LoanDto>>> fetchAll() {
    List<LoanEntity> entities = service.findAll();
    return ResponseEntity.ok(
        ApiResponse.<List<LoanDto>>builder()
            .success(true)
            .data(mapper.toDtoList(entities))
            .message(format(MESSAGE_PARAMETERIZED_RESOURCE_FOUND_WITH_PARAMETER, entities.size(), RESOURCE_TYPE_USER))
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
