package com.azubike.ellipsis.springsecuritybasic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.springsecuritybasic.model.Accounts;
import com.azubike.ellipsis.springsecuritybasic.model.Customer;
import com.azubike.ellipsis.springsecuritybasic.repository.AccountsRepository;

@RestController
public class AccountController {

	@Autowired
	private AccountsRepository accountsRepository;

	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		Accounts accounts = accountsRepository.findByCustomerId(customer.getId());
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}
	}

}
