package com.nexters.pinataserver.common.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommonResponse<T> {
	private Result result;
	private T data;

	public enum Result {
		SUCCESS, FAIL;
	}

}
