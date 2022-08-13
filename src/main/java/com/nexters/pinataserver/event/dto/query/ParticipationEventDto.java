package com.nexters.pinataserver.event.dto.query;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ParticipationEventDto {

	private Long eventId;

	private String eventCode;

	private String eventTitle;

	private boolean result;

	private String resultMessage;

	private String resultImageFileName;

	private Long itemId;

	private String itemTitle;

	private String itemImageFileName;

	private boolean isPeriod;

	private LocalDateTime openAt;

	private LocalDateTime closeAt;

	private LocalDateTime participateAt;

}
