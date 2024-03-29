package com.nexters.pinataserver.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;
import com.nexters.pinataserver.common.exception.e5xx.UnKnownException;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonApiResponse<ResponseException> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception) {
		log.error("{}", exception);

		String firstErrorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();

		return CommonApiResponse.error(
			new ResponseException(
				HttpStatus.BAD_REQUEST,
				"METHOD_ARGS_EXCEPTION",
				firstErrorMessage
			)
		);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public CommonApiResponse<ResponseException> handleExpiredAccessTokenException(
		ExpiredJwtException exception) {
		log.error("{}", exception);

		return CommonApiResponse.error(
			new ResponseException(
				HttpStatus.UNAUTHORIZED,
				"TOKEN_EXPIRED",
				"토큰이 만료되었습니다."
			)
		);
	}

	@ExceptionHandler(ResponseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonApiResponse<ResponseException> processException(ResponseException exception) {
		log.info("{}", exception);
		return CommonApiResponse.error(exception);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiResponse<ResponseException> handleException(Exception exception) {
		log.info("{}", exception);
		return CommonApiResponse.error(UnKnownException.UNKNOWN.getResponseException());
	}

}