package com.azubike.ellipsis.springsecuritybasic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.springsecuritybasic.model.Cards;
import com.azubike.ellipsis.springsecuritybasic.model.Customer;
import com.azubike.ellipsis.springsecuritybasic.repository.CardsRepository;

@RestController
public class CardsController {

	@Autowired
	private CardsRepository cardsRepository;

	@PostMapping("/myCards")
	public List<Cards> getCardDetails(@RequestBody Customer customer) {
		List<Cards> cards = cardsRepository.findByCustomerId(customer.getId());
		if (cards != null) {
			return cards;
		} else {
			return null;
		}
	}

}