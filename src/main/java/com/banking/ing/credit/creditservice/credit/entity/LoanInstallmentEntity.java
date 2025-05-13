package com.banking.ing.credit.creditservice.credit.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.banking.ing.credit.creditservice.common.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "loan-installments")
@Filter(name = "softDeleteFilter", condition = "deleted = :isDeleted")
public class LoanInstallmentEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "loan_id", referencedColumnName = "id")
  private LoanEntity loan;

  private BigDecimal amount;
  private BigDecimal paidAmount;
  private LocalDate dueDate;
  private LocalDate paymentDate;
  private boolean isPaid;

}
