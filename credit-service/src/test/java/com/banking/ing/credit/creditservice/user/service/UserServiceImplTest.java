package com.banking.ing.credit.creditservice.user.service;

import static com.banking.ing.credit.creditservice.user.enums.UserRoleType.ADMIN;
import static com.banking.ing.credit.creditservice.user.enums.UserRoleType.CUSTOMER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import com.banking.ing.credit.creditservice.user.exception.UserAlreadyExistsException;
import com.banking.ing.credit.creditservice.user.exception.UserNotFoundException;
import com.banking.ing.credit.creditservice.user.repository.UserRepository;
import com.banking.ing.credit.creditservice.user.service.impl.UserServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceImplTest {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    passwordEncoder = mock(PasswordEncoder.class);
    userService = new UserServiceImpl(userRepository, passwordEncoder);
  }

  @Test
  void shouldReturnAllUsers() {
    UserEntity user1 = new UserEntity();
    UserEntity user2 = new UserEntity();
    List<UserEntity> users = Arrays.asList(user1, user2);
    when(userRepository.findAll()).thenReturn(users);

    List<UserEntity> result = userService.findAll();

    assertThat(result).containsExactly(user1, user2);
    verify(userRepository).findAll();
  }

  @Test
  void shouldReturnUserById() {
    UserEntity user = new UserEntity();
    user.setId(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    Optional<UserEntity> result = userService.findById(1L);

    assertThat(result).isPresent().contains(user);
    verify(userRepository).findById(1L);
  }

  @Test
  void shouldFetchUserById() {
    UserEntity user = new UserEntity();
    user.setId(2L);
    when(userRepository.findById(2L)).thenReturn(Optional.of(user));

    UserEntity result = userService.fetchById(2L);

    assertThat(result).isEqualTo(user);
    verify(userRepository).findById(2L);
  }

  @Test
  void shouldThrowExceptionWhenFetchByIdFails() {
    when(userRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userService.fetchById(99L))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("99");
  }

  @Test
  void shouldFindByUsername() {
    UserEntity user = new UserEntity();
    user.setUsername("john");
    when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

    UserEntity result = userService.findByUsername("john");

    assertThat(result).isEqualTo(user);
    verify(userRepository).findByUsername("john");
  }

  @Test
  void shouldThrowWhenUsernameNotFound() {
    when(userRepository.findByUsername("no-user")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userService.findByUsername("no-user"))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("no-user");
  }

  @Test
  void shouldCreateNewUser() {
    UserEntity user = new UserEntity();
    user.setUsername("newuser");
    user.setPassword("plain");

    when(userRepository.existsByUsername("newuser")).thenReturn(false);
    when(passwordEncoder.encode("plain")).thenReturn("encoded");
    when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

    UserEntity result = userService.create(user);

    assertThat(result.getPassword()).isEqualTo("encoded");
    verify(userRepository).existsByUsername("newuser");
    verify(passwordEncoder).encode("plain");
    verify(userRepository).save(user);
  }

  @Test
  void shouldThrowWhenUserAlreadyExists() {
    UserEntity user = new UserEntity();
    user.setUsername("existing");

    when(userRepository.existsByUsername("existing")).thenReturn(true);

    assertThatThrownBy(() -> userService.create(user))
        .isInstanceOf(UserAlreadyExistsException.class)
        .hasMessageContaining("existing");
  }

  @Test
  void shouldUpdateUserRole() {
    UserEntity existing = new UserEntity();
    existing.setId(1L);
    existing.setRole(CUSTOMER);

    UserEntity update = new UserEntity();
    update.setRole(ADMIN);

    when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
    when(userRepository.save(existing)).thenReturn(existing);

    UserEntity result = userService.update(1L, update);

    assertThat(result.getRole()).isEqualTo(ADMIN);
    verify(userRepository).findById(1L);
    verify(userRepository).save(existing);
  }

  @Test
  void shouldDeleteUserSoftly() {
    UserEntity user = new UserEntity();
    user.setId(3L);
    user.setDeleted(false);

    when(userRepository.findById(3L)).thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenReturn(user);

    userService.delete(3L);

    assertThat(user.getDeleted()).isTrue();
    verify(userRepository).findById(3L);
    verify(userRepository).save(user);
  }

  @Test
  void shouldThrowWhenUpdatingNonexistentUser() {
    when(userRepository.findById(123L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userService.update(123L, new UserEntity()))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("123");
  }

  @Test
  void shouldThrowWhenDeletingNonexistentUser() {
    when(userRepository.findById(456L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userService.delete(456L))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("456");
  }
}
