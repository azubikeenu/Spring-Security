package com.azubike.ellipsis.springsecuritybasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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

		http.authorizeRequests().antMatchers("/myAccount").authenticated().antMatchers("/myLoans").authenticated()
				.antMatchers("/myBalance").authenticated().antMatchers("/myCard").authenticated()
				.antMatchers("/notices").permitAll().antMatchers("/contact").permitAll().and().formLogin().and()
				.httpBasic();

	}

//	@SuppressWarnings("deprecation")
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("Richard").password("password123").authorities("admin").and()
//				.withUser("Azubike").password("userpass").authorities("read").and()
//				.passwordEncoder(NoOpPasswordEncoder.getInstance());
//
//	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		UserDetails userOne = User.withUsername("Richard").password("pass123").authorities("admin").build();
		UserDetails userTwo = User.withUsername("Azubike").password("userpass").authorities("read").build();
		userDetailsService.createUser(userOne);
		userDetailsService.createUser(userTwo);
		auth.userDetailsService(userDetailsService);

	}

	@SuppressWarnings("deprecation")
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
