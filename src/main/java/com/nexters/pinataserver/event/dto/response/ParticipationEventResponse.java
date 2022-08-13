package com.nexters.pinataserver.event.dto.response;

import java.time.LocalDateTime;

import com.nexters.pinataserver.common.dto.response.DatePatternEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ParticipationEventResponse {
	private Long eventId;
	private String eventCode;
	private boolean result;
	private String resultMessage;
	private String resultImageUrl;
	private Long itemId;
	private String itemImageUrl;
	private boolean isPeriod;
	private LocalDateTime openAt;
	private LocalDateTime closeAt;

	public String getOpenAt() {
		return openAt.format(DatePatternEnum.DATETIME_DEFAULT.formatter());
	}

	public String getCloseAt() {
		return closeAt.format(DatePatternEnum.DATETIME_DEFAULT.formatter());
	}
}
