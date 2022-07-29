package com.nexters.pinataserver.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.nexters.pinataserver.auth.service.AuthService;
import com.nexters.pinataserver.common.exception.e4xx.AuthenticationException;
import com.nexters.pinataserver.common.security.JwtService;
import com.nexters.pinataserver.utils.HeaderUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

	private final JwtService jwtService;
	private final AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		String accessToken = HeaderUtils.getAccessToken(request);
		jwtService.verifyAccessToken(accessToken);

		String providerId = jwtService.decode(accessToken);

		if (!providerId.equals(authService.getUserByProviderId(providerId))) {
			throw new AuthenticationException();
		}

		return true;
	}
}