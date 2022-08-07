package com.nexters.pinataserver.common.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ResponseException extends RuntimeException {

	@EqualsAndHashCode.Include
	private int status;

	@EqualsAndHashCode.Include
	private String code;

	@EqualsAndHashCode.Include
	private String message;

	public ResponseException(ResponseException responseException) {
		this.status = responseException.status;
		this.code = responseException.code;
		this.message = responseException.message;
	}

	public ResponseException(HttpStatus status, String code, String message) {
		this.status = status.value();
		this.code = code;
		this.message = message;
	}

	@Override
	protected ResponseException clone() {
		return new ResponseException(this);
	}

}
