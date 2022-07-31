package com.nexters.pinataserver.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;
import com.nexters.pinataserver.common.exception.e5xx.UnKnownException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(ResponseException.class)
	public CommonApiResponse<ResponseException> processException(ResponseException exception) {
		return CommonApiResponse.error(exception);
	}

	@ExceptionHandler(Exception.class)
	public CommonApiResponse<ResponseException> handleException() {
		return CommonApiResponse.error(UnKnownException.UNKNOWN.getResponseException());
	}

}