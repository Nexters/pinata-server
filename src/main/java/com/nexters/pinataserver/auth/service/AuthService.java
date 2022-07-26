package com.nexters.pinataserver.auth.service;

import org.springframework.stereotype.Service;

import com.nexters.pinataserver.common.exception.ResponseException;
import com.nexters.pinataserver.common.security.AuthApiService;
import com.nexters.pinataserver.common.security.JwtService;
import com.nexters.pinataserver.common.security.dto.KakaoProfile;
import com.nexters.pinataserver.user.domain.User;
import com.nexters.pinataserver.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserService userService;

	private final AuthApiService authApiService;

	private final JwtService jwtService;

	public String signUp(User user) {
		userService.createUser(user);
		// TODO jwt
		return jwtService.createAccessToken("userId");
	}

	public String signIn(String accessToken) throws ResponseException {
		KakaoProfile kakaoProfile = authApiService.getProfile(accessToken);
		User user = userService.getUserByProviderId(kakaoProfile.getId());
		return jwtService.createAccessToken(user.getId().toString());
	}

}


