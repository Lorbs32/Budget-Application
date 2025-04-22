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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired private CustomUserDetailsService customerUserDetailsService;


//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
//		XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
//		// set the name of the attribute the CsrfToken will be populated on
//		delegate.setCsrfRequestAttributeName("_csrf");
//		// Use only the handle() method of XorCsrfTokenRequestAttributeHandler and the
//		// default implementation of resolveCsrfTokenValue() from CsrfTokenRequestHandler
//		CsrfTokenRequestHandler requestHandler = delegate::handle;
//		http
//				// ...
//				.csrf((csrf) -> csrf
//						.csrfTokenRepository(tokenRepository)
//						.csrfTokenRequestHandler(requestHandler)
//				);
//
//		return http.build();
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
//		XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
//		// set the name of the attribute the CsrfToken will be populated on
//		delegate.setCsrfRequestAttributeName("_csrf");
//		// Use only the handle() method of XorCsrfTokenRequestAttributeHandler and the
//		// default implementation of resolveCsrfTokenValue() from CsrfTokenRequestHandler
//		CsrfTokenRequestHandler requestHandler = delegate::handle;


		//.csrfTokenRepository(tokenRepository)
		//.csrfTokenRequestHandler(requestHandler)
		//.csrfTokenRepository(new HttpSessionCsrfTokenRepository())

//		.ignoringRequestMatchers("/addCategory")
//		.ignoringRequestMatchers("/addCategory/createCategory")

		http
				.csrf(csrf -> csrf
						.ignoringRequestMatchers("/h2-console/**")
				)
				.headers(headers -> headers
						.frameOptions(frameOptions -> frameOptions.disable())
				)
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers("/h2-console/**").permitAll().anyRequest().authenticated() //url in requestMatchers does NOT need auth, other DOES need auth
				)
				.formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/dashboard?budgetDateId=4", true))
				.logout(logout -> logout
						.logoutSuccessHandler((request, response, authentication) -> {
							response.setHeader("HX-Redirect", "/logout");
						})
				);
//				.logout(logout -> logout.logoutUrl("/logout").permitAll());
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
