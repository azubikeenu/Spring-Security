package com.azubike.ellipsis.springsecuritybasic.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

	@GetMapping("/myCard")
	public String getCardDetails(String input) {
		return "Here are the card details from the DB";
	}

}
