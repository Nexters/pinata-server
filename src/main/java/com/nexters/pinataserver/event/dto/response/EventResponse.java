package com.nexters.pinataserver.event.dto.response;

import com.nexters.pinataserver.common.dto.response.DatePatternEnum;
import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EventResponse {

	private Long id;
	private String code;
	private String title;
	private boolean isPeriod;
	private String openAt;
	private String closeAt;
	private EventStatus status;

	public static EventResponse from(Event event) {
		return EventResponse.builder()
			.id(event.getId())
			.code(event.getCode())
			.isPeriod(event.getEventDateTime().getIsPeriod())
			.title(event.getTitle())
			.status(event.getStatus())
			.openAt(event.getEventDateTime().getOpenAt().format(DatePatternEnum.DATETIME_DEFAULT.formatter()))
			.closeAt(event.getEventDateTime().getCloseAt().format(DatePatternEnum.DATETIME_DEFAULT.formatter()))
			.build();
	}

}
