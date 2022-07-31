package com.nexters.pinataserver.common.interceptor;

import static com.nexters.pinataserver.common.exception.e4xx.AuthenticationException.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.nexters.pinataserver.auth.service.AuthService;
import com.nexters.pinataserver.common.exception.ResponseException;
import com.nexters.pinataserver.common.security.JwtService;
import com.nexters.pinataserver.user.domain.User;
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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ResponseException {
		String accessToken = HeaderUtils.getAccessToken(request);
		jwtService.verifyAccessToken(accessToken);

		String email = jwtService.decode(accessToken);

		User user = authService.getUserByEmail(email);

		log.error(email + " " + user.getEmail());

		if (!email.equals(authService.getUserByEmail(email).getEmail())) {
			log.error(email + " " + authService.getUserByEmail(email).getEmail());
			throw new ResponseException(UNAUTHORIZATION.get());
		}

		return true;
	}
}