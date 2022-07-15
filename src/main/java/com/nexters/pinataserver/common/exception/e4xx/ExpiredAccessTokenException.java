package com.nexters.pinataserver.common.exception.e4xx;

import lombok.Getter;

@Getter
public class ExpiredAccessTokenException extends RuntimeException {

	private String sub;

	public ExpiredAccessTokenException() {
	}

	public ExpiredAccessTokenException(Throwable t, String sub) {
		super(t);
		this.sub = sub;
	}

	public ExpiredAccessTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
