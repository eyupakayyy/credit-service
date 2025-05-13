package com.banking.ing.credit.creditservice.common.config;

import com.banking.ing.credit.creditservice.engine.service.CreditService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

  private final CreditService creditService;

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Credit Service API")
            .version(creditService.version())
            .description("API documentation for Credit Service"));
  }

}
