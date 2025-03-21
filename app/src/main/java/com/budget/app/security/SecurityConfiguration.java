package com.budget.app.security;

import com.budget.app.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired private CustomUserDetailsService customerUserDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf
						.ignoringRequestMatchers("/h2-console/**")
				)
				.headers(headers -> headers
						.frameOptions(frameOptions -> frameOptions.disable())
				)
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated()) //url in requestMatchers does NOT need auth, other DOES need auth
				.formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/dashboard", true))
				.logout(logout -> logout.logoutUrl("/logout").permitAll());
		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); //may change to other password encoder if needed
		provider.setUserDetailsService(customerUserDetailsService);
		return provider;
	}
}
