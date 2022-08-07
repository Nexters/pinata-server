package com.nexters.pinataserver.event.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipateEventRequest {

	@NotBlank(message = "code must be not blank")
	private String code;

}
