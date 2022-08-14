package com.nexters.pinataserver.auth.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInRequest {
	@NotNull
	private Long providerId;
	@Email
	private String email;
	@NotNull
	private String nickname;
	private String profileImageUrl;
}
