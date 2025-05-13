package com.banking.ing.credit.creditservice.credit.api;

import static com.banking.ing.credit.creditservice.credit.constants.CreditRouteConstants.LOAN_BASE_URI;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.credit.dto.LoanDto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(LOAN_BASE_URI)
public interface LoanApi {

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<LoanDto>> create(@RequestBody final LoanDto dto);

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  default ResponseEntity<ApiResponse<LoanDto>> update(@PathVariable final Long id, @RequestBody final LoanDto dto) {
    throw new UnsupportedOperationException("Loan entry cannot be updated!");
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<LoanDto>> fetch(@PathVariable final Long id);

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<List<LoanDto>>> fetchAll();

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<Void>> delete(@PathVariable final Long id);

}
