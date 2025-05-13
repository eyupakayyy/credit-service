package com.banking.ing.credit.creditservice.engine.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.ing.credit.creditservice.credit.dto.LoanDto;
import com.banking.ing.credit.creditservice.credit.dto.LoanInstallmentDto;
import com.banking.ing.credit.creditservice.credit.entity.LoanEntity;
import com.banking.ing.credit.creditservice.credit.entity.LoanInstallmentEntity;
import com.banking.ing.credit.creditservice.credit.mapper.LoanInstallmentObjectMapper;
import com.banking.ing.credit.creditservice.credit.mapper.LoanObjectMapper;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanCreateResponse;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentRequest;
import com.banking.ing.credit.creditservice.credit.modal.CustomerLoanPaymentResponse;
import com.banking.ing.credit.creditservice.credit.service.LoanInstallmentService;
import com.banking.ing.credit.creditservice.credit.service.LoanService;
import com.banking.ing.credit.creditservice.engine.service.impl.LoanEngineServiceImpl;
import com.banking.ing.credit.creditservice.engine.util.LoanUtil;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import com.banking.ing.credit.creditservice.user.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class LoanEngineServiceImplTest {

  private UserService userService;
  private LoanService loanService;
  private LoanInstallmentService loanInstallmentService;
  private LoanObjectMapper loanObjectMapper;
  private LoanInstallmentObjectMapper loanInstallmentObjectMapper;
  private LoanEngineServiceImpl service;

  @BeforeEach
  void setUp() {
    userService = mock(UserService.class);
    loanService = mock(LoanService.class);
    loanInstallmentService = mock(LoanInstallmentService.class);
    loanObjectMapper = mock(LoanObjectMapper.class);
    loanInstallmentObjectMapper = mock(LoanInstallmentObjectMapper.class);

    service = new LoanEngineServiceImpl(userService, loanService, loanInstallmentService, loanObjectMapper, loanInstallmentObjectMapper);
  }

  @Test
  void shouldInitiateLoanSuccessfully() {
    // Arrange
    Long customerId = 99L;
    CustomerLoanCreateRequest request = CustomerLoanCreateRequest.builder()
        .customerId(customerId)
        .installmentCount(12)
        .interestRate(BigDecimal.valueOf(0.3))
        .amount(BigDecimal.valueOf(12000))
        .build();

    UserEntity user = new UserEntity();
    user.setId(customerId);

    LoanEntity loan = new LoanEntity();
    loan.setCustomer(user);

    LoanEntity persistedLoan = new LoanEntity();
    persistedLoan.setId(1L);
    persistedLoan.setCustomer(user);

    LoanInstallmentEntity installment1 = new LoanInstallmentEntity();
    LoanInstallmentEntity installment2 = new LoanInstallmentEntity();
    List<LoanInstallmentEntity> generatedInstallments = List.of(installment1, installment2);

    List<LoanInstallmentEntity> persistedInstallments = List.of(installment1, installment2);

    LoanDto loanDto = LoanDto.builder().id(1L).build();
    LoanInstallmentDto installmentDto1 = LoanInstallmentDto.builder().build();
    LoanInstallmentDto installmentDto2 = LoanInstallmentDto.builder().build();

    // Mock static methods from LoanUtil
    try (MockedStatic<LoanUtil> mocked = Mockito.mockStatic(LoanUtil.class)) {
      mocked.when(() -> LoanUtil.createLoan(user, request)).thenReturn(loan);
      mocked.when(() -> LoanUtil.createInstallments(loan)).thenReturn(generatedInstallments);

      when(userService.fetchById(customerId)).thenReturn(user);
      when(loanService.create(loan)).thenReturn(persistedLoan);
      when(loanInstallmentService.create(generatedInstallments)).thenReturn(persistedInstallments);
      when(loanObjectMapper.toDto(persistedLoan)).thenReturn(loanDto);
      when(loanInstallmentObjectMapper.toDtoList(persistedInstallments)).thenReturn(List.of(installmentDto1, installmentDto2));

      // Act
      CustomerLoanCreateResponse response = service.initiateLoan(request);

      // Assert
      assertThat(response).isNotNull();
      assertThat(response.getCustomerId()).isEqualTo(customerId);
      assertThat(response.getLoanDto()).isEqualTo(loanDto);
      assertThat(response.getInstallmentDto()).containsExactly(installmentDto1, installmentDto2);

      // Verify interactions
      verify(userService).fetchById(customerId);
      verify(loanService).create(loan);
      verify(loanInstallmentService).create(generatedInstallments);
      verify(loanObjectMapper).toDto(persistedLoan);
      verify(loanInstallmentObjectMapper).toDtoList(persistedInstallments);

      // Verify loan is set on each installment
      assertThat(generatedInstallments).allSatisfy(i -> assertThat(i.getLoan()).isEqualTo(persistedLoan));
    }
  }

  @Test
  void shouldProcessLoanPaymentSuccessfully() {
    // Arrange
    Long loanId = 100L;
    Long customerId = 200L;
    BigDecimal paymentAmount = BigDecimal.valueOf(300);

    CustomerLoanPaymentRequest request = CustomerLoanPaymentRequest.builder()
        .loanId(loanId)
        .customerId(customerId)
        .amount(paymentAmount)
        .build();

    LoanInstallmentEntity installment1 = new LoanInstallmentEntity();
    installment1.setAmount(BigDecimal.valueOf(100));
    installment1.setDueDate(LocalDate.now().minusDays(5));
    installment1.setPaid(false);

    LoanInstallmentEntity installment2 = new LoanInstallmentEntity();
    installment2.setAmount(BigDecimal.valueOf(100));
    installment2.setDueDate(LocalDate.now().minusDays(3));
    installment2.setPaid(false);

    List<LoanInstallmentEntity> foundInstallments = List.of(installment1, installment2);

    // Mock static methods
    try (MockedStatic<LoanUtil> mocked = Mockito.mockStatic(LoanUtil.class)) {
      mocked.when(() -> LoanUtil.fetchSortedUnPaidLoanInstallments(any()))
          .thenReturn(foundInstallments);
      mocked.when(() -> LoanUtil.calculateInstallmentPaymentAmount(any(), any()))
          .thenAnswer(invocation -> invocation.getArgument(0)); // Return the amount as-is

      when(loanInstallmentService.findAllByLoanId(loanId)).thenReturn(foundInstallments);
      when(loanInstallmentService.updateAll(any())).thenAnswer(invocation -> invocation.getArgument(0));
      when(loanInstallmentObjectMapper.toDtoList(any())).thenReturn(List.of(
          LoanInstallmentDto.builder().isPaid(true).build(),
          LoanInstallmentDto.builder().isPaid(true).build()
      ));

      // Act
      CustomerLoanPaymentResponse response = service.paymentProcess(request);

      // Assert
      assertThat(response).isNotNull();
      assertThat(response.getCustomerId()).isEqualTo(customerId);
      assertThat(response.getAvailableBalance()).isEqualTo(BigDecimal.valueOf(100));
      assertThat(response.getPaidInstallments()).hasSize(2);
      assertThat(response.getPaidInstallments()).allMatch(LoanInstallmentDto::isPaid);

      // Verify
      verify(loanInstallmentService, times(1)).findAllByLoanId(loanId);
      verify(loanInstallmentService, times(1)).updateAll(any());
      verify(loanInstallmentObjectMapper, times(1)).toDtoList(any());
    }
  }
}
