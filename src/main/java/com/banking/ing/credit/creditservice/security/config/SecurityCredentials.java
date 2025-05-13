package com.banking.ing.credit.creditservice.security.config;

import static com.banking.ing.credit.creditservice.security.constants.SecurityConstants.JWT_CREDENTIAL_SECRET_KEY;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = JWT_CREDENTIAL_SECRET_KEY)
public class SecurityCredentials {

  private String secretKey;

}
