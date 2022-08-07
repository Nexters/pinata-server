package com.nexters.pinataserver.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;
import com.nexters.pinataserver.common.security.AuthenticationPrincipal;
import com.nexters.pinataserver.user.domain.User;
import com.nexters.pinataserver.user.dto.UserResponse;
import com.nexters.pinataserver.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/users/{id}")
	public CommonApiResponse<UserResponse> getUser(@PathVariable("id") Long id) {
		User user = userService.getUserById(id);
		return CommonApiResponse.ok(UserResponse.from(user));
	}

	@GetMapping("/me")
	public CommonApiResponse<UserResponse> getMyInfo(@AuthenticationPrincipal Long userId) {
		User user = userService.getUserById(userId);
		return CommonApiResponse.ok(UserResponse.from(user));
	}

}
