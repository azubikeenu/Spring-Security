package com.azubike.ellipsis.springsecuritybasic.config;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.azubike.ellipsis.springsecuritybasic.filters.AuthoritiesLoggingAfterFilter;
import com.azubike.ellipsis.springsecuritybasic.filters.AuthoritiesLoggingAtFilter;
import com.azubike.ellipsis.springsecuritybasic.filters.JWTTokenGeneratorFilter;
import com.azubike.ellipsis.springsecuritybasic.filters.JWTTokenValidatorFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
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
//		http.cors().and().csrf().ignoringAntMatchers("/contact")
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
//				.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
//				.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//				.authorizeRequests().antMatchers("/myAccount").hasRole("USER").antMatchers("/myLoans").hasRole("ROOT")
//				.antMatchers("/myBalance").hasAnyRole("USER", "ADMIN").antMatchers("/myCard").authenticated()
//				.antMatchers("/notices").permitAll().antMatchers("/contact").permitAll().and().formLogin().and()
//				.httpBasic();

		// Using JWT-token

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors()
				.configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration config = new CorsConfiguration();
						config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
						config.setAllowedMethods(Collections.singletonList("*"));
						config.setAllowCredentials(true);
						config.setAllowedHeaders(Collections.singletonList("*"));
						config.setExposedHeaders(Arrays.asList("Authorization"));
						config.setMaxAge(3600L);
						return config;
					}
				}).and().csrf().disable()
//				.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
				.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class).authorizeRequests()
				.antMatchers("/myAccount").hasRole("USER").antMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
				.antMatchers("/myLoans").authenticated().antMatchers("/myCards").hasAnyRole("USER", "ADMIN")
				.antMatchers("/user").authenticated().antMatchers("/notices").permitAll().antMatchers("/contact")
				.permitAll().and().httpBasic();

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

}
