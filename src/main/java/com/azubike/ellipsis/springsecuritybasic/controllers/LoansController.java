package com.azubike.ellipsis.springsecuritybasic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.springsecuritybasic.model.Customer;
import com.azubike.ellipsis.springsecuritybasic.model.Loans;
import com.azubike.ellipsis.springsecuritybasic.repository.LoanRepository;

@RestController
public class LoansController {

	@Autowired
	private LoanRepository loanRepository;

	@PostMapping("/myLoans")
	public List<Loans> getLoanDetails(@RequestBody Customer customer) {
		List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId());
		if (loans != null) {
			return loans;
		} else {
			return null;
		}
	}

}