package com.nexters.pinataserver.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;
import com.nexters.pinataserver.common.exception.e4xx.AuthenticationException;
import com.nexters.pinataserver.common.exception.e4xx.ExpiredAccessTokenException;
import com.nexters.pinataserver.common.exception.e5xx.UnKnownException;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler({AuthenticationException.class, JwtException.class, ExpiredAccessTokenException.class})
	public CommonApiResponse<ResponseException> AuthenticationException(Exception exception) {
		ResponseException responseException = new ResponseException(HttpStatus.BAD_REQUEST,
			HttpStatus.BAD_REQUEST.toString(), "사용자 인증 실패");
		return CommonApiResponse.error(responseException);
	}

	@ExceptionHandler(ResponseException.class)
	public CommonApiResponse<ResponseException> processException(ResponseException exception) {
		return CommonApiResponse.error(exception);
	}

	@ExceptionHandler(Exception.class)
	public CommonApiResponse<ResponseException> handleException(Exception exception) {
		return CommonApiResponse.error(UnKnownException.UNKNOWN.getResponseException());
	}

}
