package com.azubike.ellipsis.springsecuritybasic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.springsecuritybasic.model.AccountTransactions;
import com.azubike.ellipsis.springsecuritybasic.model.Customer;
import com.azubike.ellipsis.springsecuritybasic.repository.AccountTransactionsRepository;

@RestController
public class BalanceController {

	@Autowired
	private AccountTransactionsRepository accountTransactionsRepository;

	@PostMapping("/myBalance")
	public List<AccountTransactions> getBalanceDetails(@RequestBody Customer customer) {
		List<AccountTransactions> accountTransactions = accountTransactionsRepository
				.findByCustomerIdOrderByTransactionDtDesc(customer.getId());
		if (accountTransactions != null) {
			return accountTransactions;
		} else {
			return null;
		}
	}
}