package com.azubike.ellipsis.springsecuritybasic.config;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

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

		http.cors().configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration corsConfiguration = new CorsConfiguration();
				corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				corsConfiguration.setAllowedMethods(List.of("*"));
				corsConfiguration.setAllowCredentials(true);
				corsConfiguration.setAllowedMethods(List.of("*"));
				corsConfiguration.setAllowedHeaders(List.of("*"));
				corsConfiguration.setMaxAge(3600L);
				return corsConfiguration;
			}
		}).and().authorizeRequests().antMatchers("/myAccount").authenticated().antMatchers("/myLoans").authenticated()
				.antMatchers("/myBalance").authenticated().antMatchers("/myCard").authenticated()
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

}
