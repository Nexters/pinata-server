package com.nexters.pinataserver.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nexters.pinataserver.common.interceptor.JwtInterceptor;
import com.nexters.pinataserver.common.security.AuthenticationArgumentResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final JwtInterceptor jwtInterceptor;
	private final AuthenticationArgumentResolver authenticationArgumentResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
			.addPathPatterns("/api/**")
		.excludePathPatterns("/api/ping")
		;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(authenticationArgumentResolver);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000")
				.allowedMethods("GET", "POST","PUT","DELETE","OPTIONS")
				.maxAge(3000);
	}

}
