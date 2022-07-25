package com.nexters.pinataserver.common.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nexters.pinataserver.common.exception.ResponseException;

import lombok.Getter;

@Getter
public class CommonApiResponse <T> {

	private final ApiResult result;

	private final T data;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime serverDatetime;

	protected CommonApiResponse(ApiResult result, T data) {
		this.result = result;
		this.data = data;
		this.serverDatetime = LocalDateTime.now();
	}

	public static <T> CommonApiResponse<T> ok(T response) {
		return new CommonApiResponse<>(ApiResult.SUCCESS, response);
	}

	public static CommonApiResponse<ResponseException> error(ResponseException exception) {
		return new CommonApiResponse<>(ApiResult.FAIL, exception);
	}


}