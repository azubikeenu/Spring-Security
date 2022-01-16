package com.azubike.ellipsis.springsecuritybasic.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.azubike.ellipsis.springsecuritybasic.model.Authority;
import com.azubike.ellipsis.springsecuritybasic.model.Customer;
import com.azubike.ellipsis.springsecuritybasic.repository.CustomerRepository;

@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		List<Customer> customers = customerRepository.findByEmail(username);
		if (customers.size() > 0) {
			if (passwordEncoder.matches(password, customers.get(0).getPwd())) {
				List<GrantedAuthority> authorities = getAuthorities(customers.get(0).getAuthorities());
				// this returns an authentication object
				return new UsernamePasswordAuthenticationToken(username, customers, authorities);
			} else {
				throw new BadCredentialsException("Invalid password");
			}

		} else {
			throw new BadCredentialsException(" User not found");
		}

	}

	public List<GrantedAuthority> getAuthorities(Set<Authority> authorities) {
		List<GrantedAuthority> auths = new ArrayList<>();
		for (Authority authority : authorities) {
			auths.add(new SimpleGrantedAuthority(authority.getName()));
		}
		return auths;
	}

	@Override
	public boolean supports(Class<?> authenticationType) {
		return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
	}

}
