package com.banking.ing.credit.creditservice.credit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.ing.credit.creditservice.credit.entity.CustomerCreditEntity;
import com.banking.ing.credit.creditservice.credit.exception.CreditNotFoundException;
import com.banking.ing.credit.creditservice.credit.exception.CustomerCreditAlreadyExistsException;
import com.banking.ing.credit.creditservice.credit.respository.CustomerCreditRepository;
import com.banking.ing.credit.creditservice.credit.service.impl.CustomerCreditServiceImpl;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerCreditServiceImplTest {

  @Mock
  private CustomerCreditRepository repository;

  @InjectMocks
  private CustomerCreditServiceImpl service;

  private CustomerCreditEntity mockEntity;

  private UserEntity mockCustomer;

  @BeforeEach
  void setUp() {
    mockCustomer = new UserEntity();
    mockCustomer.setId(1L);

    mockEntity = new CustomerCreditEntity();
    mockEntity.setId(10L);
    mockEntity.setCustomer(mockCustomer);
    mockEntity.setCreditLimit(BigDecimal.valueOf(10000));
    mockEntity.setCreditScore(750);
  }

  @Test
  void fetch_shouldReturnEntity_whenIdExists() {
    when(repository.findById(10L)).thenReturn(Optional.of(mockEntity));

    CustomerCreditEntity result = service.fetch(10L);

    assertNotNull(result);
    assertEquals(10L, result.getId());
    verify(repository).findById(10L);
  }

  @Test
  void fetch_shouldThrowException_whenIdDoesNotExist() {
    when(repository.findById(99L)).thenReturn(Optional.empty());

    assertThrows(CreditNotFoundException.class, () -> service.fetch(99L));
    verify(repository).findById(99L);
  }

  @Test
  void fetchByCustomerId_shouldReturnEntity_whenCustomerExists() {
    when(repository.findByCustomerId(1L)).thenReturn(Optional.of(mockEntity));

    CustomerCreditEntity result = service.fetchByCustomerId(1L);

    assertEquals(1L, result.getCustomer().getId());
    verify(repository).findByCustomerId(1L);
  }

  @Test
  void fetchByCustomerId_shouldThrowException_whenCustomerNotExists() {
    when(repository.findByCustomerId(2L)).thenReturn(Optional.empty());

    assertThrows(CreditNotFoundException.class, () -> service.fetchByCustomerId(2L));
    verify(repository).findByCustomerId(2L);
  }

  @Test
  void findAll_shouldReturnList() {
    List<CustomerCreditEntity> list = List.of(mockEntity);
    when(repository.findAll()).thenReturn(list);

    List<CustomerCreditEntity> result = service.findAll();

    assertEquals(1, result.size());
    verify(repository).findAll();
  }

  @Test
  void create_shouldSaveEntity_whenCustomerNotExists() {
    when(repository.existsByCustomer(mockCustomer)).thenReturn(false);
    when(repository.save(mockEntity)).thenReturn(mockEntity);

    CustomerCreditEntity result = service.create(mockEntity);

    assertEquals(mockEntity, result);
    verify(repository).existsByCustomer(mockCustomer);
    verify(repository).save(mockEntity);
  }

  @Test
  void create_shouldThrowException_whenCustomerAlreadyExists() {
    when(repository.existsByCustomer(mockCustomer)).thenReturn(true);

    assertThrows(CustomerCreditAlreadyExistsException.class, () -> service.create(mockEntity));
    verify(repository).existsByCustomer(mockCustomer);
    verify(repository, never()).save(any());
  }

  @Test
  void update_shouldModifyEntity_whenIdExists() {
    CustomerCreditEntity updated = new CustomerCreditEntity();
    updated.setCreditLimit(BigDecimal.valueOf(5000));
    updated.setCreditScore(600);
    updated.setCustomer(mockCustomer);

    when(repository.findById(10L)).thenReturn(Optional.of(mockEntity));
    when(repository.save(any())).thenReturn(mockEntity);

    CustomerCreditEntity result = service.update(10L, updated);

    assertEquals(600, result.getCreditScore());
    assertEquals(BigDecimal.valueOf(5000), result.getCreditLimit());
  }

  @Test
  void update_shouldThrowException_whenIdNotFound() {
    CustomerCreditEntity dummy = new CustomerCreditEntity();
    dummy.setCreditScore(500);
    dummy.setCreditLimit(BigDecimal.valueOf(1000));

    when(repository.findById(99L)).thenReturn(Optional.empty());

    assertThrows(CreditNotFoundException.class, () -> service.update(99L, dummy));
    verify(repository, never()).save(any());
  }

  @Test
  void delete_shouldMarkEntityDeleted_whenIdExists() {
    when(repository.findById(10L)).thenReturn(Optional.of(mockEntity));

    service.delete(10L);

    assertTrue(mockEntity.getDeleted());
    verify(repository).save(mockEntity);
  }

  @Test
  void deleteByCustomerId_shouldMarkEntityDeleted_whenCustomerExists() {
    when(repository.findByCustomerId(1L)).thenReturn(Optional.of(mockEntity));

    service.deleteByCustomerId(1L);

    assertTrue(mockEntity.getDeleted());
    verify(repository).save(mockEntity);
  }

  @Test
  void delete_shouldThrowException_whenIdNotFound() {
    when(repository.findById(100L)).thenReturn(Optional.empty());

    assertThrows(CreditNotFoundException.class, () -> service.delete(100L));
    verify(repository, never()).save(any());
  }

  @Test
  void deleteByCustomerId_shouldThrowException_whenCustomerNotFound() {
    when(repository.findByCustomerId(2L)).thenReturn(Optional.empty());

    assertThrows(CreditNotFoundException.class, () -> service.deleteByCustomerId(2L));
    verify(repository, never()).save(any());
  }
}
