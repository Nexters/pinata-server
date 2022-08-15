package com.nexters.pinataserver.event.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.pinataserver.common.exception.e4xx.NotFoundException;
import com.nexters.pinataserver.common.util.ImageUtil;
import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventItem;
import com.nexters.pinataserver.event.domain.EventRepository;
import com.nexters.pinataserver.event.dto.query.ParticipationEventDto;
import com.nexters.pinataserver.event.dto.response.OrganizersEventResponse;
import com.nexters.pinataserver.event.dto.response.ReadCurrentParticipateEventResponse;
import com.nexters.pinataserver.event.dto.response.ReadParticipateEventsResponse;
import com.nexters.pinataserver.event.dto.response.ReadEventResponse;
import com.nexters.pinataserver.event_history.domain.EventHistory;
import com.nexters.pinataserver.event_history.domain.EventHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventReadService {

	private final EventRepository eventRepository;

	private final EventValidateService eventValidateService;

	private final EventHistoryRepository eventHistoryRepository;

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

	@Transactional(readOnly = true)
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
				.resultImageUrl(
					participationEventDto.isResult() ?  imageUtil.getFullImageUrl(participationEventDto.getHitImageFileName()) : imageUtil.getFullImageUrl(participationEventDto.getMissImageFileName()))
				.itemId(participationEventDto.getItemId())
				.itemTitle(
					participationEventDto.isResult() ? participationEventDto.getItemTitle() : null
					)
				.itemImageUrl(imageUtil.getFullImageUrl(participationEventDto.getItemImageFileName()))
				.openAt(participationEventDto.getOpenAt())
				.closeAt(participationEventDto.getCloseAt())
				.participateAt(participationEventDto.getParticipateAt())
				.resultMessage(
					participationEventDto.isResult() ? participationEventDto.getHitMessage() : participationEventDto.getMissMessage()
				)
				.build())
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ReadEventResponse getEvent(Long userId, String eventCode) {

		// 이벤트 조회
		Event foundEvent = eventRepository.findByCode(eventCode)
			.orElseThrow(NotFoundException.EVENT);

		return convertToReadEventResponse(foundEvent);
	}

	private ReadEventResponse convertToReadEventResponse(Event foundEvent) {
		return ReadEventResponse.builder()
			.id(foundEvent.getId())
			.title(foundEvent.getTitle())
			.code(foundEvent.getCode())
			.status(foundEvent.getStatus())
			.isPeriod(foundEvent.getEventDateTime().getIsPeriod())
			.openAt(foundEvent.getEventDateTime().getOpenAt())
			.closeAt(foundEvent.getEventDateTime().getCloseAt())
			.type(foundEvent.getType())
			.totalParticipantCount(foundEvent.getParticipantCount())
			.items(foundEvent.getEventItems().stream()
				.map(this::convertToReadEventItemResult)
				.collect(Collectors.toList()))
			.build();
	}

	// TODO : 이벤트 상세조회 시 N+1문제 해결 필요
	private ReadEventResponse.EventItemResult convertToReadEventItemResult(EventItem eventItem) {

		EventHistory foundEventHistory = getEventHistory(eventItem);

		return ReadEventResponse.EventItemResult.builder()
			.id(eventItem.getId())
			.rank(eventItem.getRanking())
			.imageUrl(imageUtil.getFullImageUrl(eventItem.getImageFileName()))
			.isAccepted(eventItem.isAccepted())
			.acceptorEmail(foundEventHistory.getParticipantEmail())
			.acceptorNickname(foundEventHistory.getParticipantName())
			.build();
	}

	private EventHistory getEventHistory(EventItem eventItem) {
		EventHistory empty = EventHistory.builder().build();

		return eventHistoryRepository.findByEventId(eventItem.getEvent().getId())
			.orElse(empty);
	}

}
