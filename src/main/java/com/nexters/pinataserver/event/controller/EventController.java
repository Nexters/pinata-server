package com.nexters.pinataserver.event.controller;

import static com.nexters.pinataserver.event.controller.EventController.*;

import javax.validation.Valid;

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
import com.nexters.pinataserver.event.dto.request.ParticipateEventRequest;
import com.nexters.pinataserver.event.dto.request.RegisterEventRequest;
import com.nexters.pinataserver.event.dto.response.ParticipateEventResponse;
import com.nexters.pinataserver.event.dto.response.ReadCurrentParticipateEventResponse;
import com.nexters.pinataserver.event.dto.response.RegisterEventResponse;
import com.nexters.pinataserver.event.service.EventCreateService;
import com.nexters.pinataserver.event.service.EventReadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URI)
public class EventController {

	public static final String BASE_URI = "/api/v1/events";

	private final EventReadService eventReadService;

	private final EventCreateService eventCreateService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/participate/{eventCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonApiResponse<ReadCurrentParticipateEventResponse> readCurrentParticipateEvent(
		@PathVariable("eventCode") String eventCode
		// @AuthenticationPrincipal Long userId
	) {
		ReadCurrentParticipateEventResponse response = eventReadService.getParticipateEvent(1L, eventCode);

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

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public CommonApiResponse<RegisterEventResponse> registerEvent(
		@Valid @RequestBody RegisterEventRequest request
		// @AuthenticationPrincipal Long userId
	) {
		Long userId = 1L;
		RegisterEventResponse response = eventCreateService.createEvent(userId, request);

		return CommonApiResponse.<RegisterEventResponse>ok(response);
	}

}
