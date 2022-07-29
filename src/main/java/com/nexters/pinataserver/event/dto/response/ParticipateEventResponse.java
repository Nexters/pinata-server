package com.nexters.pinataserver.event.dto.response;

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

}
