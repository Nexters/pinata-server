package com.nexters.pinataserver.common.exception.e4xx;

import org.springframework.http.HttpStatus;

import com.nexters.pinataserver.common.exception.ResponseDefinition;
import com.nexters.pinataserver.common.exception.ResponseException;

public enum EventTimeException implements ResponseDefinition {

	TIME_OUT(HttpStatus.BAD_REQUEST, "ERR1004", "이벤트 참여 기간이 아닙니다."),
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "ERR1005", "이벤트 기간 입력이 잘못되었습니다.");
	private final ResponseException responseException;

	EventTimeException(HttpStatus status, String code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}

}
