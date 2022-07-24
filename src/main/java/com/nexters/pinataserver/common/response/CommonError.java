package com.nexters.pinataserver.common.response;

import lombok.Getter;

@Getter
public class CommonError {

	private String code;
	private String message;

	CommonError(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
