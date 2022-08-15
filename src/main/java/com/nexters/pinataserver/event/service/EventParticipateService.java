package com.nexters.pinataserver.event.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.pinataserver.common.exception.e4xx.EventTimeException;
import com.nexters.pinataserver.common.exception.e4xx.NotFoundException;
import com.nexters.pinataserver.common.exception.e4xx.NotParticipateTargetException;
import com.nexters.pinataserver.common.util.ImageUtil;
import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventItem;
import com.nexters.pinataserver.event.domain.EventRepository;
import com.nexters.pinataserver.event.domain.EventType;
import com.nexters.pinataserver.event.dto.response.ParticipateEventResponse;
import com.nexters.pinataserver.event_history.domain.EventHistory;
import com.nexters.pinataserver.event_history.domain.EventHistoryRepository;
import com.nexters.pinataserver.user.domain.User;
import com.nexters.pinataserver.user.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventParticipateService {

	private final EventRepository eventRepository;

	private final EventHistoryRepository eventHistoryRepository;

	private final UserRepository userRepository;

	private final EventValidateService eventValidateService;

	private final FixStatusService fixEventStatusService;

	private final ImageUtil imageUtil;

	public ParticipateEventResponse participateEvent(Long participantId, String eventCode) {

		// 참가자 조회
		User participant = userRepository.findById(participantId)
			.orElseThrow(NotFoundException.USER);

		// TODO : 분리할 로직
		// 기간 + 현재 상태 체크 후 수정
		fixEventStatusService.fixEventStatus(eventCode);

		// 이벤트 조회
		Event foundEvent = eventRepository.findByCodeForUpdate(eventCode)
			.orElseThrow(NotFoundException.EVENT);
		if (Objects.equals(participant.getId(), foundEvent.getOrganizerId())) {
			throw NotParticipateTargetException.TARGET.getResponseException();
		}

		// 이벤트 참가 가능한 이벤트인지 검증
		eventValidateService.validateCanParticipate(participantId, foundEvent);
		// TODO : 분리할 로직
		if (foundEvent.getEventDateTime().isBeforeOpenDateTime(LocalDateTime.now())) {
		    throw EventTimeException.TIME_OUT.get();
		}

		// 게임 유형별로 당첨여부 판단
		EventType eventType = foundEvent.getType();
		boolean isHit = eventType.isHit(foundEvent);

		// 이벤트 결과 처리
		EventItem hitEventItem = null;
		if (isHit) {
			foundEvent.hit();
			hitEventItem = foundEvent.getHitEventItem();
			hitEventItem.accept();
		} else {
			foundEvent.miss();
			hitEventItem = EventItem.builder().build();
		}

		// 이벤트 참여 기록 저장
		writeEventHistory(participant, foundEvent, hitEventItem, isHit);

		return makeParticipateEventResponse(foundEvent, hitEventItem, isHit);
	}

	private void writeEventHistory(User participant, Event foundEvent, EventItem hitEventItem,
		boolean isHit) {
		EventHistory eventHistory = EventHistory.builder()
			.eventId(foundEvent.getId())
			.eventItemId(hitEventItem.getId())
			.title(foundEvent.getTitle())
			.participantId(participant.getId())
			.participantName(participant.getNickname())
			.participantEmail(participant.getEmail())
			.isHit(isHit)
			.build();
		eventHistoryRepository.save(eventHistory);
	}

	private ParticipateEventResponse makeParticipateEventResponse(
		Event foundEvent,
		EventItem hitEventItem,
		boolean isHit
	) {
		String resultMessage = isHit ? foundEvent.getHitMessage() : foundEvent.getMissMessage();
		String resultImageUrl = isHit ? imageUtil.getFullImageUrl(foundEvent.getHitImageFileName())
			: imageUtil.getFullImageUrl(foundEvent.getMissImageFileName());
		Long hitEventItemId = (hitEventItem.getId() != null) ? hitEventItem.getId() : null;
		String hitItemTitle = (hitEventItem.getTitle() != null) ? imageUtil.getFullImageUrl(
			hitEventItem.getImageFileName()) : null;
		String hitItemImageUrl = (hitEventItem.getImageFileName() != null) ? imageUtil.getFullImageUrl(
			hitEventItem.getImageFileName()) : null;

		return ParticipateEventResponse.builder()
			.result(isHit)
			.code(foundEvent.getCode())
			.resultMessage(resultMessage)
			.resultImageURL(resultImageUrl)
			.itemId(hitEventItemId)
			.itemTitle(hitItemTitle)
			.itemImageUrl(hitItemImageUrl)
			.build();
	}

}
