package com.nexters.pinataserver.common.exception.e4xx;

import org.springframework.http.HttpStatus;

import com.nexters.pinataserver.common.exception.ResponseDefinition;
import com.nexters.pinataserver.common.exception.ResponseException;

public enum NotParticipateTargetException  implements ResponseDefinition {

	TARGET(HttpStatus.BAD_REQUEST, "ERR1006", "이벤트 참여 대상이 아닙니다.");

	private final ResponseException responseException;

	NotParticipateTargetException(HttpStatus status, String code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}

}
