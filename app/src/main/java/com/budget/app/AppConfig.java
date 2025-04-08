package com.budget.app;

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
}
