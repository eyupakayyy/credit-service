package com.banking.ing.credit.creditservice.credit.respository;

import com.banking.ing.credit.creditservice.common.repository.base.BaseRepository;
import com.banking.ing.credit.creditservice.credit.entity.CustomerCreditEntity;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface CustomerCreditRepository extends BaseRepository<CustomerCreditEntity, Long> {

  @Query("SELECT c FROM CustomerCreditEntity c WHERE c.customer.id = :customerId AND c.deleted = false")
  Optional<CustomerCreditEntity> findByCustomerId(final Long customerId);

  boolean existsByCustomer(final UserEntity customer);

}