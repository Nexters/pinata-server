package com.nexters.pinataserver.event.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.pinataserver.common.util.ImageUtil;
import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventDateTime;
import com.nexters.pinataserver.event.domain.EventItem;
import com.nexters.pinataserver.event.domain.EventRepository;
import com.nexters.pinataserver.event.dto.request.RegisterEventRequest;
import com.nexters.pinataserver.event.dto.response.RegisterEventResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventCreateService {

	private final EventRepository eventRepository;

	private final ImageUtil imageUtil;

	public RegisterEventResponse createEvent(Long userId, RegisterEventRequest request) {
		Event savedEvent = eventRepository.save(convertToEvent(userId, request));

		return RegisterEventResponse.of(savedEvent.getCode());
	}

	private Event convertToEvent(Long userId, RegisterEventRequest request) {
		return Event.builder()
			.organizerId(userId)
			.code(UUID.randomUUID().toString())
			.type(request.getType())
			.eventDateTime(
				convertToEventDateTime(request)
			)
			.hitImageFileName(imageUtil.extractImageFileName(request.getHitImageUrl()))
			.hitMessage(request.getHitMessage())
			.missImageFileName(imageUtil.extractImageFileName(request.getMissImageUrl()))
			.missMessage(request.getMissMessage())
			.title(request.getTitle())
			.limitCount(request.getItems().size())
			.eventItems(
				convertToEventItems(request)
			)
			.build();
	}

	private EventDateTime convertToEventDateTime(RegisterEventRequest request) {
		return EventDateTime.builder()
			.isPeriod(request.isPeriod())
			.openAt(request.getOpenAt())
			.closeAt(request.getCloseAt())
			.build();
	}

	private List<EventItem> convertToEventItems(RegisterEventRequest request) {
		return request.getItems().stream()
			.map(this::convertToEventItem).collect(Collectors.toList());
	}

	private EventItem convertToEventItem(RegisterEventRequest.EventItemParam itemParam) {
		return EventItem.builder()
			.title(itemParam.getTitle())
			.imageFileName(imageUtil.extractImageFileName(itemParam.getImageUrl()))
			.ranking(itemParam.getRank())
			.build();
	}

}
