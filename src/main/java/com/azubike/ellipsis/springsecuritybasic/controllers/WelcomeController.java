package com.azubike.ellipsis.springsecuritybasic.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping("/")
	String showWelcomePage() {
		return "Welcome to Spring Security course";
	}

}
