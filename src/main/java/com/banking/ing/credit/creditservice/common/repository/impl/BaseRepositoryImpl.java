package com.banking.ing.credit.creditservice.common.repository.impl;

import com.banking.ing.credit.creditservice.common.entity.base.BaseEntity;
import com.banking.ing.credit.creditservice.common.repository.base.BaseRepository;
import jakarta.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class BaseRepositoryImpl<T extends BaseEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

  private final EntityManager entityManager;

  public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
    enableSoftDeleteFilter();
  }

  @Override
  public List<T> findAll() {
    enableSoftDeleteFilter();
    return super.findAll();
  }

  @Override
  public Optional<T> findById(ID id) {
    enableSoftDeleteFilter();
    String jpql = "SELECT e FROM " + getDomainClass().getSimpleName() + " e WHERE e.id = :id AND e.deleted = false";

    T result = entityManager.createQuery(jpql, getDomainClass())
        .setParameter("id", id)
        .getResultStream()
        .findFirst()
        .orElse(null);

//    return super.findById(id);
    return Optional.ofNullable(result);
  }

  @Override
  public boolean existsById(ID id) {
    enableSoftDeleteFilter();
    return super.existsById(id);
  }

  @Override
  public long count() {
    enableSoftDeleteFilter();
    return super.count();
  }

  private void enableSoftDeleteFilter() {
    Session session = entityManager.unwrap(Session.class);
    if (session.getEnabledFilter("softDeleteFilter") == null) {
      session.enableFilter("softDeleteFilter").setParameter("isDeleted", false);
    }
  }
}
