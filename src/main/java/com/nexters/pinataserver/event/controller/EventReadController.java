package com.nexters.pinataserver.event.controller;

import static com.nexters.pinataserver.event.controller.EventReadController.*;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;
import com.nexters.pinataserver.common.security.AuthenticationPrincipal;
import com.nexters.pinataserver.event.domain.EventStatus;
import com.nexters.pinataserver.event.domain.EventType;
import com.nexters.pinataserver.event.dto.request.ParticipateEventRequest;
import com.nexters.pinataserver.event.dto.response.ParticipateEventResponse;
import com.nexters.pinataserver.event.dto.response.ReadCurrentParticipateEventResponse;
import com.nexters.pinataserver.event.service.EventReadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URI)
public class EventReadController {

	public static final String BASE_URI = "/api/v1/events";

	private final EventReadService eventReadService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/participate/{eventCode}", produces = MediaType.APPLICATION_JSON_VALUE)

	public CommonApiResponse<ReadCurrentParticipateEventResponse> readCurrentParticipateEvent(
		@PathVariable("eventCode") String eventCode,
		@AuthenticationPrincipal Long userId
	) {
		LocalDateTime now = LocalDateTime.now();
		ReadCurrentParticipateEventResponse response = ReadCurrentParticipateEventResponse.builder()
			.code(eventCode)
			.type(EventType.FCFS)
			.status(EventStatus.PROCESS)
			.isPeriod(true)
			.openAt(now.plusSeconds(20))
			.closeAt(now.plusHours(1))
			.hitImageUrl("https://kr.object.ncloudstorage.com/pinata-bucket/images/hit-image.jpeg")
			.hitMessage("축하합니다~ 남은 넥스터즈 기간도 화이팅~!")
			.missImageUrl("https://kr.object.ncloudstorage.com/pinata-bucket/images/miss-image.jpeg")
			.missMessage("메롱~~~~")
			.title("넥스터즈 21기 깜짝 선물 3분께 드립니다.")
			.build();

		// ReadCurrentParticipateEventResponse response = eventReadService.getParticipateEvent(userId, eventCode);

		return CommonApiResponse.<ReadCurrentParticipateEventResponse>ok(response);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(
		value = "/participate",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public CommonApiResponse<ParticipateEventResponse> participateEvent(
		@RequestBody ParticipateEventRequest request
	) {
		ParticipateEventResponse response = ParticipateEventResponse.builder()
			.code(request.getCode())
			.itemId(1L)
			.itemTitle("스타벅스 아메리카노 톨 사이즈")
			.result(true)
			.resultImageURL("https://kr.object.ncloudstorage.com/pinata-bucket/images/hit-image.jpeg")
			.resultMessage("당첨되셨습니다. 축하드립니다! 선물 받기 버튼을 눌러 이미지를 저장하세요.")
			.itemImageUrl("https://kr.object.ncloudstorage.com/pinata-bucket/images/product-image.jpeg")
			.build();

		return CommonApiResponse.<ParticipateEventResponse>ok(response);
	}

}
