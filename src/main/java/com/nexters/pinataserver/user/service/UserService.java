package com.nexters.pinataserver.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.pinataserver.common.exception.ResponseException;
import com.nexters.pinataserver.common.exception.e4xx.NotFoundException;
import com.nexters.pinataserver.user.domain.User;
import com.nexters.pinataserver.user.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public User getUserByEmail(String email) throws ResponseException {
		return userRepository.findByEmail(email).orElseThrow(NotFoundException.USER);
	}

}
