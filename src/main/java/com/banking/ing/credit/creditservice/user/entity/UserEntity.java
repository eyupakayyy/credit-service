package com.banking.ing.credit.creditservice.user.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.banking.ing.credit.creditservice.common.entity.base.BaseEntity;
import com.banking.ing.credit.creditservice.user.enums.UserRoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "users")
//@FilterDef(name = "softDeleteFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "softDeleteFilter", condition = "deleted = :isDeleted")
public class UserEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String username;
  private String password;
  private UserRoleType role;

}
