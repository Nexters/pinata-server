package com.nexters.pinataserver.common.exception.e5xx;

import org.springframework.http.HttpStatus;

import com.nexters.pinataserver.common.exception.ResponseDefinition;
import com.nexters.pinataserver.common.exception.ResponseException;

public enum UnKnownException implements ResponseDefinition {

	UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN", "서버 에러로 인해 데이터를 로드 할 수 없습니다.");

	private final ResponseException responseException;

	UnKnownException(HttpStatus status, String code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}

}
