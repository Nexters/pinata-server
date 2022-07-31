package com.nexters.pinataserver.auth.service;

import org.springframework.stereotype.Service;

import com.nexters.pinataserver.common.exception.ResponseException;
import com.nexters.pinataserver.common.security.JwtService;
import com.nexters.pinataserver.user.domain.User;
import com.nexters.pinataserver.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserService userService;
	private final JwtService jwtService;

	public String signUp(User user) {
		User currentUser = userService.createUser(user);
		return jwtService.createAccessToken(currentUser.getEmail());
	}

	public String signIn(String email) throws ResponseException {
		User currentUser = userService.getUserByEmail(email);
		return jwtService.createAccessToken(currentUser.getEmail());
	}

	public User getUserByEmail(String email) throws ResponseException {
		return userService.getUserByEmail(email);
	}

}
