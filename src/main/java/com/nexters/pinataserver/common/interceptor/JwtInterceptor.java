package com.nexters.pinataserver.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.nexters.pinataserver.common.security.JwtService;
import com.nexters.pinataserver.utils.HeaderUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

	private final JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String accessToken = HeaderUtils.getAccessToken(request);
		jwtService.verifyAccessToken(accessToken);
		jwtService.decode(accessToken);
		return true;
	}
}