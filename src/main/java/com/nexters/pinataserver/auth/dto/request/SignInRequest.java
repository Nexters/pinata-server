package com.nexters.pinataserver.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInRequest {
	private Long providerId;
	private String email;
	private String nickname;
	private String profileImageUrl;
}
