package com.nexters.pinataserver.common.response;

public class ResponseEntity<T> {
	public static <T> CommonResponse<T> create(T response) {
		return new CommonResponse(CommonResponse.Result.SUCCESS, response);
	}

	public static <T> CommonResponse<T> ok() {
		return new CommonResponse(CommonResponse.Result.SUCCESS, null);
	}

	public static <T> CommonResponse<T> error(String code, String message) {
		return new CommonResponse(CommonResponse.Result.FAIL, new CommonError(code, message));
	}
}
