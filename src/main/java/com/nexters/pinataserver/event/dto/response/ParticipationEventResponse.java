package com.nexters.pinataserver.event.dto.response;

import com.nexters.pinataserver.common.dto.response.DatePatternEnum;
import com.nexters.pinataserver.common.util.ImageUtil;

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
	private String openAt;
	private String closeAt;

	public static ParticipationEventResponse from(ParticipationEventDTO participationEvent) {
		return ParticipationEventResponse.builder()
			.eventId(participationEvent.getEventId())
			.eventCode(participationEvent.getEventCode())
			.result(participationEvent.isResult())
			.resultMessage(participationEvent.getResultMessage())
			.resultImageUrl(ImageUtil.getFullImageUrl(participationEvent.getResultImageUrl()))
			.itemId(participationEvent.getItemId())
			.itemImageUrl(ImageUtil.getFullImageUrl(participationEvent.getItemImageUrl()))
			.isPeriod(participationEvent.isPeriod())
			.openAt(participationEvent.getOpenAt().format(DatePatternEnum.DATETIME_DEFAULT.formatter()))
			.closeAt(participationEvent.getCloseAt().format(DatePatternEnum.DATETIME_DEFAULT.formatter()))
			.build();
	}

}
