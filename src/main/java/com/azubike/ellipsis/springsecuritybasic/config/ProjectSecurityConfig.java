package com.azubike.ellipsis.springsecuritybasic.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.azubike.ellipsis.springsecuritybasic.filters.AuthoritiesLoggingAfterFilter;
import com.azubike.ellipsis.springsecuritybasic.filters.RequestValidationBeforeFilter;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * myAccounts -- secured myLoans -- secured myBalance -- secured myCard ---
		 * secured notices -- public contact -- public
		 */
		// default configuration
		// http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();

		// configuration to deny all requests
		// http.authorizeRequests().anyRequest().denyAll().and().formLogin().and().httpBasic();

		// configuration to permit all requests
		// http.authorizeRequests().anyRequest().permitAll().and().formLogin().and().httpBasic();

		// custom configuration

		// Authorities Authorization
//		http.cors().and().csrf().ignoringAntMatchers("/contact")
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().authorizeRequests()
//				.antMatchers("/myAccount").hasAuthority("WRITE").antMatchers("/myLoans").hasAnyAuthority("READ")
//				.antMatchers("/myBalance").hasAnyAuthority("DELETE").antMatchers("/myCard").authenticated()
//				.antMatchers("/notices").permitAll().antMatchers("/contact").permitAll().and().formLogin().and()
//				.httpBasic();

		// Roles Authorization
		http.cors().and().csrf().ignoringAntMatchers("/contact")
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/myAccount").hasRole("USER").antMatchers("/myLoans").hasRole("ROOT")
				.antMatchers("/myBalance").hasAnyRole("USER", "ADMIN").antMatchers("/myCard").authenticated()
				.antMatchers("/notices").permitAll().antMatchers("/contact").permitAll().and().formLogin().and()
				.httpBasic();

	}

// inMemoryAuthentication
//	@SuppressWarnings("deprecation")
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("Richard").password("password123").authorities("admin").and()
//				.withUser("Azubike").password("userpass").authorities("read").and()
//				.passwordEncoder(NoOpPasswordEncoder.getInstance());
//				
//
//	}
	// InMemoryUserDetailsManager
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//		UserDetails userThree = new User("Sandra", "azubike88", List.of());
//		UserDetails userOne = User.withUsername("Richard").password("pass123").authorities("admin").build();
//		UserDetails userTwo = User.withUsername("Azubike").password("userpass").authorities("read").build();
//		userDetailsService.createUser(userOne);
//		userDetailsService.createUser(userTwo);
//		auth.userDetailsService(userDetailsService);
//
//	}

	// Using default UserDetailsSchema for authentication
//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}

	@Bean
	PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "PUT", "DELETE", "POST"));
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
		corsConfiguration.setMaxAge(3600L);
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;

	}

}
