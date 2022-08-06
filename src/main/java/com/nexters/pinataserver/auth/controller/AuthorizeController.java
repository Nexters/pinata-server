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
import com.nexters.pinataserver.common.dto.response.CommonApiResponse;
import com.nexters.pinataserver.common.exception.ResponseException;
import com.nexters.pinataserver.user.domain.User;
import com.nexters.pinataserver.user.domain.UserState;

import lombok.RequiredArgsConstructor;

@RequestMapping("/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthorizeController {

	private final AuthService authService;

	@PostMapping("/signup")
	public CommonApiResponse<SignUpResponse> signUp(@RequestBody SignUpRequest request) {
		User user = User.builder()
			.providerId(request.getProviderId())
			.nickname(request.getNickname())
			.email(request.getEmail())
			.profileImageUrl(request.getProfileImageUrl())
			.state(UserState.ACTIVE)
			.build();
		String accessToken = authService.signUp(user);
		SignUpResponse response = new SignUpResponse(accessToken);
		return CommonApiResponse.ok(response);
	}

	@PostMapping("/signin")
	public CommonApiResponse<SignInResponse> signIn(@RequestBody SignInRequest request) throws ResponseException {
		String accessToken = authService.signIn(request.getEmail());
		SignInResponse response = new SignInResponse(accessToken);
		return CommonApiResponse.ok(response);
	}

}