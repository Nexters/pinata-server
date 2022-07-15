package com.nexters.pinataserver.common.exception.e4xx;

import com.nexters.pinataserver.common.exception.ResponseDefinition;
import com.nexters.pinataserver.common.exception.ResponseException;
import org.springframework.http.HttpStatus;

public enum NotFoundException implements ResponseDefinition {

	USER(HttpStatus.BAD_REQUEST, NotFoundException.NOT_FOUND_CODE, "해당 사용자가 존재하지 않습니다.");

	private static final int NOT_FOUND_CODE = 404;

	private final ResponseException responseException;

	NotFoundException(HttpStatus status, Integer code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}

}
