package com.nexters.pinataserver.event.dto.response;

import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventDateTime;
import com.nexters.pinataserver.event.domain.EventStatus;
import com.nexters.pinataserver.event.domain.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EventResponse {

	private String title;
	private EventDateTime eventDateTime;
	private EventType type;
	private EventStatus status;
	private Integer limitCount;
	private Integer hitCount;
	private Integer participantCount;
	private String hitImageFileName;
	private String hitMessage;
	private String missImageFileName;
	private String missMessage;

	public static EventResponse from(Event event) {
		return EventResponse.builder()
			.title(event.getTitle())
			.eventDateTime(event.getEventDateTime())
			.type(event.getType())
			.status(event.getStatus())
			.limitCount(event.getLimitCount())
			.hitCount(event.getHitCount())
			.participantCount(event.getParticipantCount())
			.hitImageFileName(event.getHitImageFileName())
			.hitMessage(event.getHitMessage())
			.missImageFileName(event.getMissImageFileName())
			.missMessage(event.getMissMessage())
			.build();
	}

}
