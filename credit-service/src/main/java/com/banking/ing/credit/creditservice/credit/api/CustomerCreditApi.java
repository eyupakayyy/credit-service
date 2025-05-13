package com.banking.ing.credit.creditservice.credit.api;

import static com.banking.ing.credit.creditservice.credit.constants.CreditRouteConstants.CUSTOMER_CREDIT_BASE_URI;

import com.banking.ing.credit.creditservice.common.api.response.base.ApiResponse;
import com.banking.ing.credit.creditservice.credit.dto.CustomerCreditDto;
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

@RequestMapping(CUSTOMER_CREDIT_BASE_URI)
public interface CustomerCreditApi {

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<CustomerCreditDto>> create(@RequestBody final CustomerCreditDto request);

  @GetMapping("/customer/{customerId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<CustomerCreditDto>> fetchByCustomerId(@PathVariable final Long customerId);

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<CustomerCreditDto>> fetch(@PathVariable final Long id);

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<List<CustomerCreditDto>>> fetchAll();

  @PutMapping("/{customerId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<CustomerCreditDto>> update(@PathVariable final Long customerId, @RequestBody final CustomerCreditDto request);

  @DeleteMapping("/customer/{customerId}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<Void>> deleteByCustomerId(@PathVariable final Long customerId);

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  ResponseEntity<ApiResponse<Void>> delete(@PathVariable final Long id);

}
