package com.nexters.pinataserver.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.pinataserver.auth.dto.request.SignInRequest;
import com.nexters.pinataserver.auth.dto.response.SignInResponse;
import com.nexters.pinataserver.auth.service.AuthService;
import com.nexters.pinataserver.common.dto.response.CommonApiResponse;
import com.nexters.pinataserver.common.exception.ResponseException;
import com.nexters.pinataserver.user.domain.User;
import com.nexters.pinataserver.user.domain.UserState;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthorizeController {

	private final AuthService authService;

	@PostMapping("/signin")
	public CommonApiResponse<SignInResponse> signUp(@RequestBody SignInRequest request) throws ResponseException {
		if (!authService.existsByEmail(request.getEmail())) {
			User user = User.builder()
				.providerId(request.getProviderId())
				.nickname(request.getNickname())
				.email(request.getEmail())
				.profileImageUrl(request.getProfileImageUrl())
				.state(UserState.ACTIVE)
				.build();
			return CommonApiResponse.ok(new SignInResponse(authService.signUp(user)));
		}

		SignInResponse response = new SignInResponse(authService.signIn(request.getProviderId()));
		return CommonApiResponse.ok(response);
	}

}