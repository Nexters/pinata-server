package com.nexters.pinataserver.event.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ParticipateEventResponse {

	private final String code;

	private final boolean result;

	private final String resultMessage;

	private final String resultImageURL;

	private final Long itemId;

	private final String itemTitle;

	private final String itemImageUrl;

	@Builder
	public ParticipateEventResponse(
		String code,
		boolean result,
		String resultMessage,
		String resultImageURL,
		Long itemId,
		String itemTitle,
		String itemImageUrl
	) {
		this.code = code;
		this.result = result;
		this.resultMessage = resultMessage;
		this.resultImageURL = resultImageURL;
		this.itemId = itemId;
		this.itemTitle = itemTitle;
		this.itemImageUrl = itemImageUrl;
	}

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

}
