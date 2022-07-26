package com.nexters.pinataserver.common.security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nexters.pinataserver.common.security.dto.KakaoProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthApiService {

	private final RestTemplate restTemplate;

	public static final String BEARER_PREFIX = "Bearer ";

	// TODO properties 정의
	private String contentType;
	private String grantType;
	private String clientId;
	private String redirectUri;
	private String clientSecret;
	private String tokenUri;
	private String userInfoUri;

	public KakaoProfile getProfile(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		headers.add(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + accessToken);

		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<KakaoProfile> response = restTemplate.exchange(
			userInfoUri,
			HttpMethod.GET,
			request,
			KakaoProfile.class
		);

		return response.getBody();
	}

}
