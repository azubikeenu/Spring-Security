package com.azubike.ellipsis.springsecuritybasic.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.azubike.ellipsis.springsecuritybasic.model.Customer;
import com.azubike.ellipsis.springsecuritybasic.model.SecurityCustomer;
import com.azubike.ellipsis.springsecuritybasic.repository.CustomerRepository;

@Service
public class EazyBankUserDetails implements UserDetailsService {
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Customer> customers = customerRepository.findByEmail(username);
		if (customers.size() == 0)
			throw new UsernameNotFoundException("No record found for " + username);

		return new SecurityCustomer(customers.get(0));
	}

}
