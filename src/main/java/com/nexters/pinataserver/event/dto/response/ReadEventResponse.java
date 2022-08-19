package com.nexters.pinataserver.event.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nexters.pinataserver.event.domain.EventStatus;
import com.nexters.pinataserver.event.domain.EventType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadEventResponse {

	private Long id;

	private String title;

	private String code;

	private EventType type;

	private EventStatus status;

	private String hitMessage;

	private String hitImageUrl;

	private String missMessage;

	private String missImageUrl;

	private boolean isPeriod;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime openAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime closeAt;

	private Integer totalParticipantCount;

	private List<EventItemResult> items;

	public ReadEventResponse(Long id, String title, String code, EventType type, EventStatus status, String hitMessage,
		String hitImageUrl, String missMessage, String missImageUrl, boolean isPeriod, LocalDateTime openAt,
		LocalDateTime closeAt, Integer totalParticipantCount, List<EventItemResult> items) {
		this.id = id;
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
		this.totalParticipantCount = totalParticipantCount;
		this.items = items;
	}

	@Getter
	@Builder
	public static class EventItemResult {
		private Long id;
		private String title;
		private String imageUrl;
		private Integer rank;
		private boolean isAccepted;
		private String acceptorEmail;
		private String acceptorNickname;
		private String acceptorProfileImageUrl;

		public EventItemResult(Long id, String title, String imageUrl, Integer rank, boolean isAccepted,
			String acceptorEmail, String acceptorNickname, String acceptorProfileImageUrl) {
			this.id = id;
			this.title = title;
			this.imageUrl = imageUrl;
			this.rank = rank;
			this.isAccepted = isAccepted;
			this.acceptorEmail = acceptorEmail;
			this.acceptorNickname = acceptorNickname;
			this.acceptorProfileImageUrl = acceptorProfileImageUrl;
		}

	}

}
