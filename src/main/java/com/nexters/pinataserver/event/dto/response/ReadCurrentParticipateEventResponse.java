package com.nexters.pinataserver.event.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nexters.pinataserver.event.domain.EventStatus;
import com.nexters.pinataserver.event.domain.EventType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadCurrentParticipateEventResponse {

	private final String title;

	private final String code;

	private final EventType type;

	private final EventStatus status;

	private final String hitMessage;

	private final String hitImageUrl;

	private final String missMessage;

	private final String missImageUrl;

	private final boolean isPeriod;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime openAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime closeAt;

	@Builder
	public ReadCurrentParticipateEventResponse(
		String title,
		String code,
		EventType type,
		EventStatus status,
		String hitMessage,
		String hitImageUrl,
		String missMessage,
		String missImageUrl,
		boolean isPeriod,
		LocalDateTime openAt,
		LocalDateTime closeAt
	) {
		this.title = title;
		this.code = code;
		this.type = type;
		this.status = status;
		this.hitMessage = hitMessage;
		this.hitImageUrl = hitImageUrl;
		this.missMessage = missMessage;
		this.missImageUrl = missImageUrl;
		this.isPeriod = isPeriod;
		this.openAt = openAt;
		this.closeAt = closeAt;
	}

}
