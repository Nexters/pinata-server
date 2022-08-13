package com.nexters.pinataserver.event.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParticipationEventDTO {
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
}
