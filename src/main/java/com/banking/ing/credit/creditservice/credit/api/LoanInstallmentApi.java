package com.banking.ing.credit.creditservice.credit.api;

import static com.banking.ing.credit.creditservice.credit.constants.CreditRouteConstants.LOAN_INSTALLMENT_BASE_URI;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.credit.dto.LoanInstallmentDto;
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

@RequestMapping(LOAN_INSTALLMENT_BASE_URI)
public interface LoanInstallmentApi {

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<LoanInstallmentDto>> create(@RequestBody final LoanInstallmentDto dto);

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<LoanInstallmentDto>> update(@PathVariable final Long id, @RequestBody final LoanInstallmentDto dto);

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<LoanInstallmentDto>> fetch(@PathVariable final Long id);

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<List<LoanInstallmentDto>>> fetchAll();

  @GetMapping("/loan/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<List<LoanInstallmentDto>>> fetchByLoanId(@PathVariable final Long id);

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  default ResponseEntity<ApiResponse<Void>> delete(@PathVariable final Long id) {
    throw new UnsupportedOperationException("Loan installment cannot be deleted!");
  }

}
