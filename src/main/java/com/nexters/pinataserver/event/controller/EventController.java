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
import com.nexters.pinataserver.common.security.AuthenticationPrincipal;
import com.nexters.pinataserver.event.dto.request.ParticipateEventRequest;
import com.nexters.pinataserver.event.dto.request.RegisterEventRequest;
import com.nexters.pinataserver.event.dto.response.ParticipateEventResponse;
import com.nexters.pinataserver.event.dto.response.ReadCurrentParticipateEventResponse;
import com.nexters.pinataserver.event.dto.response.RegisterEventResponse;
import com.nexters.pinataserver.event.service.EventCreateService;
import com.nexters.pinataserver.event.service.EventParticipateService;
import com.nexters.pinataserver.event.service.EventReadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URI)
public class EventController {

	public static final String BASE_URI = "/api/v1/events";

	private final EventReadService eventReadService;

	private final EventCreateService eventCreateService;

	private final EventParticipateService eventParticipateService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/participate/{eventCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommonApiResponse<ReadCurrentParticipateEventResponse> readCurrentParticipateEvent(
		@PathVariable("eventCode") String eventCode,
		@AuthenticationPrincipal Long userId
	) {
		ReadCurrentParticipateEventResponse response = eventReadService.getParticipateEvent(userId, eventCode);

		return CommonApiResponse.<ReadCurrentParticipateEventResponse>ok(response);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(
		value = "/participate",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public CommonApiResponse<ParticipateEventResponse> participateEvent(
		@Valid @RequestBody ParticipateEventRequest request,
		@AuthenticationPrincipal Long userId
	) {
		ParticipateEventResponse response = eventParticipateService.participateEvent(
			userId,
			request.getCode()
		);

		return CommonApiResponse.<ParticipateEventResponse>ok(response);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public CommonApiResponse<RegisterEventResponse> registerEvent(
		@Valid @RequestBody RegisterEventRequest request,
		@AuthenticationPrincipal Long userId
	) {
		RegisterEventResponse response = eventCreateService.createEvent(userId, request);

		return CommonApiResponse.<RegisterEventResponse>ok(response);
	}

}
