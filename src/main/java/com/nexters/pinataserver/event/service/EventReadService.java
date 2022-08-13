package com.nexters.pinataserver.event.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.pinataserver.common.exception.e4xx.NotFoundException;
import com.nexters.pinataserver.common.util.ImageUtil;
import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventRepository;
import com.nexters.pinataserver.event.dto.query.ParticipationEventDto;
import com.nexters.pinataserver.event.dto.response.OrganizersEventResponse;
import com.nexters.pinataserver.event.dto.response.ReadCurrentParticipateEventResponse;
import com.nexters.pinataserver.event.dto.response.ReadParticipateEventsResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventReadService {

	private final EventRepository eventRepository;

	private final EventValidateService eventValidateService;

	private final ImageUtil imageUtil;

	@Transactional
	public ReadCurrentParticipateEventResponse getParticipateEvent(Long participantId, String eventCode) {

		// 이벤트 조회
		Event foundEvent = eventRepository.findByCode(eventCode)
			.orElseThrow(NotFoundException.EVENT);

		// 이벤트 참가 가능한 이벤트인지 검증
		eventValidateService.validateCanParticipate(participantId, foundEvent);

		return convertToReadCurrentParticipateEventResponse(foundEvent);
	}

	private ReadCurrentParticipateEventResponse convertToReadCurrentParticipateEventResponse(Event foundEvent) {
		return ReadCurrentParticipateEventResponse.builder()
			.title(foundEvent.getTitle())
			.code(foundEvent.getCode())
			.status(foundEvent.getStatus())
			.type(foundEvent.getType())
			.hitMessage(foundEvent.getHitMessage())
			.hitImageUrl(
				imageUtil.getFullImageUrl(foundEvent.getHitImageFileName())
			)
			.missMessage(foundEvent.getMissMessage())
			.missImageUrl(
				imageUtil.getFullImageUrl(foundEvent.getMissImageFileName())
			)
			.isPeriod(foundEvent.getEventDateTime().getIsPeriod())
			.openAt(foundEvent.getEventDateTime().getOpenAt())
			.closeAt(foundEvent.getEventDateTime().getCloseAt())
			.build();
	}

	public List<OrganizersEventResponse> getEvents(Long userId, Pageable pageable) {
		return eventRepository.getMyEvents(userId, pageable).stream()
			.map(OrganizersEventResponse::from)
			.collect(Collectors.toList());
	}

	public List<ReadParticipateEventsResponse> getParticipationEvents(Long userId, Pageable pageable) {
		List<ParticipationEventDto> participationEvents = eventRepository.getParticipationEvents(userId, pageable);

		return participationEvents.stream()
			.map(participationEventDto -> ReadParticipateEventsResponse.builder()
				.eventId(participationEventDto.getEventId())
				.eventTitle(participationEventDto.getEventTitle())
				.eventCode(participationEventDto.getEventCode())
				.result(participationEventDto.isResult())
				.resultImageUrl(imageUtil.getFullImageUrl(participationEventDto.getResultImageFileName()))
				.itemId(participationEventDto.getItemId())
				.itemTitle(participationEventDto.getItemTitle())
				.itemImageUrl(imageUtil.getFullImageUrl(participationEventDto.getItemImageFileName()))
				.openAt(participationEventDto.getOpenAt())
				.closeAt(participationEventDto.getCloseAt())
				.participateAt(participationEventDto.getParticipateAt())
				.build())
			.collect(Collectors.toList());
	}

}
