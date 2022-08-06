package com.nexters.pinataserver.user.dto;

import javax.persistence.Convert;

import com.nexters.pinataserver.user.domain.User;
import com.nexters.pinataserver.user.domain.UserState;
import com.nexters.pinataserver.user.domain.UserStateConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
	private String email;
	private String profileImgUrl;
	private String nickname;
	private String profileImageUrl;
	@Convert(converter = UserStateConverter.class)
	private UserState state;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.email(user.getEmail())
			.nickname(user.getNickname())
			.profileImageUrl(user.getProfileImageUrl())
			.state(user.getState())
			.build();
	}

}
