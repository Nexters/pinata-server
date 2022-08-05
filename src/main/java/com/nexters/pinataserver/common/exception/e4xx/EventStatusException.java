package com.nexters.pinataserver.common.exception.e4xx;

import org.springframework.http.HttpStatus;

import com.nexters.pinataserver.common.exception.ResponseDefinition;
import com.nexters.pinataserver.common.exception.ResponseException;

public enum EventStatusException implements ResponseDefinition {

	COMPLETE(HttpStatus.BAD_REQUEST, "ERR1002", "이미 완료된 이벤트입니다."),
	CANCEL(HttpStatus.BAD_REQUEST, "ERR1003", "해당 이벤트는 취소되었습니다."),
	;

	private final ResponseException responseException;

	EventStatusException(HttpStatus status, String code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}

}
