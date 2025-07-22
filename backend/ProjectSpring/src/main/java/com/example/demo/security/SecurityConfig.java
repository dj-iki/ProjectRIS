package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.repositories.AppUserRepository;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	AppUserDetailsService auds;

	@Autowired
	AuthorizationFilter authorizationFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(request -> request
				.requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/login.jsp")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/registration.jsp")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
				.anyRequest().authenticated())
				.csrf(customizer -> customizer.disable())
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	AuthenticationManager athenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
			throws Exception {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	private SecurityScheme createAPIKeyScheme() {
//		return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
//	}
//
//	@Bean
//	OpenAPI openAPI() {
//		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
//				.components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
//
//	}
}
