package com.nexters.pinataserver.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class UserController {

	@GetMapping("/users/{id}")
	public void getUser(@PathVariable("id") Long id) {
	}

	@GetMapping("/me")
	public void getMyInfo() {
	}

}
