package com.nexters.pinataserver.common.exception.e4xx;

import org.springframework.http.HttpStatus;

import com.nexters.pinataserver.common.exception.ResponseDefinition;
import com.nexters.pinataserver.common.exception.ResponseException;

public enum DuplicatedException implements ResponseDefinition {

	EVENT_HISTORY(HttpStatus.BAD_REQUEST, "ERR1001", "이미 참여한 이벤트입니다.");

	private final ResponseException responseException;

	DuplicatedException(HttpStatus status, String code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}

}
