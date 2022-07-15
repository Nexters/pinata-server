package com.nexters.pinataserver.common.exception.e4xx;

import lombok.Getter;

@Getter
public class ExpiredRefreshTokenException extends RuntimeException {

	private String sub;

	public ExpiredRefreshTokenException() {
	}

	public ExpiredRefreshTokenException(Throwable t, String sub) {
		super(t);
		this.sub = sub;
	}

	public ExpiredRefreshTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
