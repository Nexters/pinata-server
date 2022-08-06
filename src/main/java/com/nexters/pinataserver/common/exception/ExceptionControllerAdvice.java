package com.nexters.pinataserver.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;
import com.nexters.pinataserver.common.exception.e5xx.UnKnownException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(ResponseException.class)
	public CommonApiResponse<ResponseException> processException(ResponseException exception) {
		log.info("{}", exception);
		return CommonApiResponse.error(exception);
	}

	@ExceptionHandler(Exception.class)
	public CommonApiResponse<ResponseException> handleException(Exception exception) {
		log.info("{}", exception);
		return CommonApiResponse.error(UnKnownException.UNKNOWN.getResponseException());
	}

}