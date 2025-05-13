package com.banking.ing.credit.creditservice.security.constants;

public final class SecurityConstants {

  public static final String JWT_CREDENTIAL_SECRET_KEY = "jwt";
  public static final String[] AUTHENTICATION_WHITE_LIST = {
      "/v1/auth/login",
      "/h2-console/**",
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/v3/api-docs",
      "/v3/api-docs**",
      "/v3/api-docs/**",
      "/swagger-resources/**",
      "/webjars/**"
  };

  private SecurityConstants() {
  }

}
