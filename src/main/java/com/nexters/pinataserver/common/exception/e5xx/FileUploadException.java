package com.nexters.pinataserver.common.exception.e5xx;

import org.springframework.http.HttpStatus;

import com.nexters.pinataserver.common.exception.ResponseDefinition;
import com.nexters.pinataserver.common.exception.ResponseException;

public enum FileUploadException implements ResponseDefinition {

	IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "ERR5001", "이미지 업로드에 실패했습니다.");

	private final ResponseException responseException;

	FileUploadException(HttpStatus status, String code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}

}
