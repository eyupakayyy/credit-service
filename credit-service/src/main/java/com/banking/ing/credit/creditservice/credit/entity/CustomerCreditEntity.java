package com.banking.ing.credit.creditservice.credit.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.banking.ing.credit.creditservice.common.entity.base.BaseEntity;
import com.banking.ing.credit.creditservice.user.entity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.envers.Audited;

@Getter
@Setter
@Entity
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer-credit")
@Filter(name = "softDeleteFilter", condition = "deleted = :isDeleted")
public class CustomerCreditEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private UserEntity customer;

  private BigDecimal creditLimit;

  private int creditScore;

}
