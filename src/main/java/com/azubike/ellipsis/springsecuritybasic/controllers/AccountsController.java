package com.azubike.ellipsis.springsecuritybasic.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {
	@GetMapping("/myAccount")
	public String getAccountDetails(String input) {
		return "Here are the account details from the DB";
	}

}
