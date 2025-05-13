package com.banking.ing.credit.creditservice.user.service.impl;

import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import com.banking.ing.credit.creditservice.user.exception.UserAlreadyExistsException;
import com.banking.ing.credit.creditservice.user.exception.UserNotFoundException;
import com.banking.ing.credit.creditservice.user.repository.UserRepository;
import com.banking.ing.credit.creditservice.user.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @Override
  public List<UserEntity> findAll() {
    return userRepository.findAll();
  }

  @Override
  public Optional<UserEntity> findById(final Long id) {
    return userRepository.findById(id);
  }

  @Override
  public UserEntity fetchById(final Long id) {
    return findById(id)
        .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
  }

  @Override
  public UserEntity findByUsername(final String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

  @Override
  public UserEntity create(final UserEntity user) {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new UserAlreadyExistsException(user.getUsername());
    }
    user.setPassword(encoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public UserEntity update(final Long id, final UserEntity entity) {
    UserEntity user = fetchUserById(id);
    user.setRole(entity.getRole());
    return userRepository.save(user);
  }

  @Override
  public void delete(final Long id) {
    UserEntity user = fetchUserById(id);
    user.setDeleted(true);
    userRepository.save(user);
  }

  private UserEntity fetchUserById(final Long id) {
    return findById(id)
        .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
  }
}
