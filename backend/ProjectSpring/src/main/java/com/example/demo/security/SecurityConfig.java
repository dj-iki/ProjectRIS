package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	AppUserDetailsService auds;

	@Autowired
	AuthorizationFilter authorizationFilter;
	
	@Autowired
	CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(request -> request
				.requestMatchers(new AntPathRequestMatcher("/")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/login.jsp")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/registration.jsp")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/search/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/index.jsp")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/countrySearch.jsp")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/citySearch.jsp")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/airportSearch.jsp")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/flightSearch.jsp")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/style/**")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/booking/**")).hasAnyRole("USER","EMPLOYEE","MANAGER","ADMIN")
				.requestMatchers(new AntPathRequestMatcher("/bookingTickets.jsp")).hasAnyRole("USER","EMPLOYEE","MANAGER","ADMIN")
				.requestMatchers(new AntPathRequestMatcher("/account/**")).hasAnyRole("USER","EMPLOYEE","MANAGER","ADMIN")
				.requestMatchers(new AntPathRequestMatcher("/updateAccount.jsp")).hasAnyRole("USER","EMPLOYEE","MANAGER","ADMIN")
				.requestMatchers(new AntPathRequestMatcher("/employee/**")).hasRole("EMPLOYEE")
				.requestMatchers(new AntPathRequestMatcher("/employeeFlight.jsp")).hasRole("EMPLOYEE")
				.requestMatchers(new AntPathRequestMatcher("/manager/**")).hasRole("MANAGER")
				.requestMatchers(new AntPathRequestMatcher("/delayFlight.jsp")).hasRole("MANAGER")
				.requestMatchers(new AntPathRequestMatcher("/addingFlights.jsp")).hasRole("MANAGER")
				.requestMatchers(new AntPathRequestMatcher("/addingPlane.jsp")).hasRole("MANAGER")
				.requestMatchers(new AntPathRequestMatcher("/hireUser.jsp")).hasRole("MANAGER")
				.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
				.requestMatchers(new AntPathRequestMatcher("/admin.jsp")).hasRole("ADMIN")
				.anyRequest().authenticated())
				.exceptionHandling(ex -> ex
						.authenticationEntryPoint(jwtAuthenticationEntryPoint)
						.accessDeniedHandler(customAccessDeniedHandler)
				)
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
