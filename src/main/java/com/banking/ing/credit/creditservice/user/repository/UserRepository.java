package com.banking.ing.credit.creditservice.user.repository;

import com.banking.ing.credit.creditservice.common.repository.base.BaseRepository;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import java.util.Optional;

public interface UserRepository extends BaseRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);

  boolean existsByUsername(String username);

}