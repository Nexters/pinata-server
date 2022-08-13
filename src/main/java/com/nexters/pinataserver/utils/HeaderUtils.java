package com.nexters.pinataserver.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderUtils {

	public static final String BEARER_PREFIX = "Bearer ";

	public static String getAccessToken(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info("========================================");
		log.info(request.getHeader(HttpHeaders.AUTHORIZATION));
		log.info("========================================");

		if (StringUtils.hasText(token)) {
			return token.substring(BEARER_PREFIX.length());
		}

		return null;
	}

	public static String getAccessToken(WebRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info("========================================");
		log.info(request.getHeader(HttpHeaders.AUTHORIZATION));
		log.info("========================================");

		if (StringUtils.hasText(token)) {
			return token.substring(BEARER_PREFIX.length());
		}

		return null;
	}

}
