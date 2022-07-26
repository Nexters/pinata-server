package com.nexters.pinataserver.common.security.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;

@Getter
public class KakaoAccount {

	private Profile profile;
	private String email;

	@Getter
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Profile {
		private String nickname;
		private String profileImageUrl;
	}

}