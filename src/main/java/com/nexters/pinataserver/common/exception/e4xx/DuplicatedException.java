package com.nexters.pinataserver.common.exception.e4xx;

import org.springframework.http.HttpStatus;

import com.nexters.pinataserver.common.exception.ResponseDefinition;
import com.nexters.pinataserver.common.exception.ResponseException;

public enum DuplicatedException implements ResponseDefinition {

	EVENT_HISTORY(HttpStatus.BAD_REQUEST, "ERR2003", "이미 참여한 이벤트입니다."),
	EMAIL(HttpStatus.BAD_REQUEST, "ERR2004", "이미 가입한 사용자입니다.");

	private final ResponseException responseException;

	DuplicatedException(HttpStatus status, String code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}

}
