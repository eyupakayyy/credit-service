package com.banking.ing.credit.creditservice.user.service;

import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import java.util.List;
import java.util.Optional;

public interface UserService {

  List<UserEntity> findAll();

  Optional<UserEntity> findById(final Long id);

  UserEntity fetchById(final Long id);

  UserEntity findByUsername(final String username);

  UserEntity create(final UserEntity user);

  UserEntity update(final Long id, final UserEntity entity);

  void delete(Long id);

}
