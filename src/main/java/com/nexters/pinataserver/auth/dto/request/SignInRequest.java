package com.nexters.pinataserver.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInRequest {
	// TODO client 와 상의 후 수정 예정
	private String accessToken;
}
