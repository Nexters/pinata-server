package com.nexters.pinataserver.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class SimpleCorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		// res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Expose-Headers", "Authorization, X-Total-Count, Link, authorization");
		res.setHeader("Access-Control-Allow-Headers",
			"Origin, X-Requested-With, Content-Type, Accept, Authorization, authorization");

		log.info("======================================== CorsFilter");
		log.info(req.getHeader(HttpHeaders.AUTHORIZATION));
		log.info("======================================== CorsFilter");


		if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}

	}
}
