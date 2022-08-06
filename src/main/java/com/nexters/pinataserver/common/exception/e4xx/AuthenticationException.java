package com.nexters.pinataserver.common.exception.e4xx;

import org.springframework.http.HttpStatus;

import com.nexters.pinataserver.common.exception.ResponseDefinition;
import com.nexters.pinataserver.common.exception.ResponseException;

public enum AuthenticationException implements ResponseDefinition {

	UNAUTHORIZATION(HttpStatus.BAD_REQUEST, "ERR0002", "사용자 인증 실패");

	private final ResponseException responseException;

	AuthenticationException(HttpStatus status, String code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}

}