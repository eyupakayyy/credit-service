package com.banking.ing.credit.creditservice.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {

  @Bean
  public Gson gsonPretty() {
    return new GsonBuilder()
        .serializeNulls()
        .setPrettyPrinting()
        .create();
  }

  @Bean
  public Gson gson() {
    return new GsonBuilder()
        .serializeNulls()
        .create();
  }

}