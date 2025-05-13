package com.banking.ing.credit.creditservice.common.config;

import static com.banking.ing.credit.creditservice.security.util.AuthenticationUtil.getCurrentUsernameOptional;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class DefaultAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return getCurrentUsernameOptional();
  }
}
