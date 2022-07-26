package com.nexters.pinataserver.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.pinataserver.auth.dto.request.SignInRequest;
import com.nexters.pinataserver.auth.dto.request.SignUpRequest;
import com.nexters.pinataserver.auth.dto.response.SignInResponse;
import com.nexters.pinataserver.auth.dto.response.SignUpResponse;
import com.nexters.pinataserver.auth.service.AuthService;
import com.nexters.pinataserver.common.exception.ResponseException;
import com.nexters.pinataserver.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth/v1")
@RestController
@RequiredArgsConstructor
public class AuthorizeController {

	private final AuthService authService;

	@PostMapping("/signup")
	public void signUp(@RequestBody SignUpRequest request) {
		User user = User.builder()
			.providerId(request.getProviderId())
			.nickname(request.getNickname())
			.email(request.getEmail())
			.profileImageUrl(request.getProfileImageUrl())
			.build();
		String accessToken = authService.signUp(user);
		SignUpResponse response = new SignUpResponse(accessToken);
		// TODO CommonResponse
	}

	@PostMapping("/signin")
	public void signIn(@RequestBody SignInRequest request) throws ResponseException {
		String accessToken = authService.signIn(request.getAccessToken());
		SignInResponse response = new SignInResponse(accessToken);
		// TODO CommonResponse
	}

}