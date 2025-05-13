package com.banking.ing.credit.creditservice.common.config;

import static com.banking.ing.credit.creditservice.common.constants.InfrastructureConstants.BASE_SERVICE_PACKAGE;

import com.banking.ing.credit.creditservice.common.repository.impl.BaseRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(
    basePackages = BASE_SERVICE_PACKAGE,
    repositoryBaseClass = BaseRepositoryImpl.class
)
@EnableTransactionManagement
public class JpaConfig {

  @Bean
  public AuditorAware<String> auditorProvider() {
    return new DefaultAuditorAware();
  }

}
