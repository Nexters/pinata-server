package com.nexters.pinataserver.event.dto.response;

import lombok.Getter;

@Getter
public class RegisterEventResponse {

	private String eventCode;

	private RegisterEventResponse(String eventCode) {
		this.eventCode = eventCode;
	}

	public static RegisterEventResponse of(String eventCode) {
		return new RegisterEventResponse((eventCode));
	}

}
