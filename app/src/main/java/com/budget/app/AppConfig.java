package com.budget.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class AppConfig
{
    @Bean
    public LocalDate todaysDate()
    {
        return LocalDate.now();
    }
}
