package com.budget.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig
{
    @Bean
    public LocalDate todaysDate() { return LocalDate.now(); }

    @Bean
    public DateTimeFormatter customFormatter() { return DateTimeFormatter.ofPattern("yyyy-MM-dd"); }
//
//    @Value("${plaid_client_id}")
//    public String plaidClientId;
//
//    @Value("${plaid_secret}")
//    public String plaidSecret;
//
//    @Value("${plaid_products}")
//    public String plaidProducts;
//
//    @Value("${plaid_country_codes}")
//    public String plaidCountryCodes;
//
//    @Value("${plaid_env}")
//    public String plaidEnv;
}
