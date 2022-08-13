package com.nexters.pinataserver.event.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadParticipateEventsResponse {

	private Long eventId;

	private String eventCode;

	private String eventTitle;

	private boolean result;

	private String resultMessage;

	private String resultImageUrl;

	private Long itemId;

	private String itemTitle;

	private String itemImageUrl;

	private boolean isPeriod;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime openAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime closeAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime participateAt;

	@Builder
	public ReadParticipateEventsResponse(Long eventId, String eventCode, String eventTitle, boolean result,
		String resultMessage, String resultImageUrl, Long itemId, String itemTitle, String itemImageUrl,
		boolean isPeriod,
		LocalDateTime openAt, LocalDateTime closeAt, LocalDateTime participateAt) {
		this.eventId = eventId;
		this.eventCode = eventCode;
		this.eventTitle = eventTitle;
		this.result = result;
		this.resultMessage = resultMessage;
		this.resultImageUrl = resultImageUrl;
		this.itemId = itemId;
		this.itemTitle = itemTitle;
		this.itemImageUrl = itemImageUrl;
		this.isPeriod = isPeriod;
		this.openAt = openAt;
		this.closeAt = closeAt;
		this.participateAt = participateAt;
	}

}
