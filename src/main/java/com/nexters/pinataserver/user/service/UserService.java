package com.nexters.pinataserver.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public boolean existsById(Long userId) {
		return userRepository.existsById(userId);
	}

	@Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Transactional(readOnly = true)
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(NotFoundException.USER);
	}

	@Transactional(readOnly = true)
	public User getUserByProviderId(Long providerId) {
		return userRepository.findByProviderId(providerId).orElseThrow((NotFoundException.USER));
	}

}
