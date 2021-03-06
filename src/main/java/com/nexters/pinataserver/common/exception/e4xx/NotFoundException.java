package com.nexters.pinataserver.common.exception.e4xx;

import com.nexters.pinataserver.common.exception.ResponseDefinition;
import com.nexters.pinataserver.common.exception.ResponseException;

import org.springframework.http.HttpStatus;
public enum NotFoundException implements ResponseDefinition {

	USER(HttpStatus.BAD_REQUEST, "ERR0001", "해당 사용자가 존재하지 않습니다.");
	private final ResponseException responseException;

	NotFoundException(HttpStatus status, String code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}

}
