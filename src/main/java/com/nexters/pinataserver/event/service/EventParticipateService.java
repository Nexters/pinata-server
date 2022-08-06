package com.nexters.pinataserver.event.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.pinataserver.common.exception.e4xx.DuplicatedException;
import com.nexters.pinataserver.common.exception.e4xx.EventStatusException;
import com.nexters.pinataserver.common.exception.e4xx.EventTimeException;
import com.nexters.pinataserver.common.exception.e4xx.NotFoundException;
import com.nexters.pinataserver.common.util.ImageUtil;
import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventDateTime;
import com.nexters.pinataserver.event.domain.EventItem;
import com.nexters.pinataserver.event.domain.EventRepository;
import com.nexters.pinataserver.event.domain.EventStatus;
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

	private final ImageUtil imageUtil;


	public ParticipateEventResponse participateEvent(Long participantId, String eventCode) {

		// 참가자 조회
		User participant = userRepository.findById(participantId)
			.orElseThrow(NotFoundException.USER);

		// 이벤트 조회
		Event foundEvent = eventRepository.findByCodeForUpdate(eventCode)
			.orElseThrow(NotFoundException.EVENT);

		// 이벤트 참가 가능한 이벤트인지 검증
		validateCanParticipate(participantId, foundEvent);

		// 게임 유형별로 당첨여부 판단
		EventType eventType = foundEvent.getType();
		EventItem hitEventItem = null;
		boolean isHit = false;
		if (eventType.isFCFS()) {

			if (foundEvent.getHitCount() <= foundEvent.getLimitCount()) {
				foundEvent.hit();
				isHit = true;
				hitEventItem = foundEvent.getHitEventItem();
			} else{
				foundEvent.miss();
			}

		} else if (eventType.isRANDOM()) {
			int bound = 100;
			int hitPercentage = 30;
			Random random = new Random();
			int randomNumber = random.nextInt(bound);
			isHit = randomNumber <= hitPercentage;
			if (isHit) {
				hitEventItem = foundEvent.getHitEventItem();
			}
			// 당첨은 됐는데 상품이 없다면? -> 탈락처리
			if (isHit && hitEventItem == null) {
				isHit = false;
			}
		}

		// 이벤트 참여 기록 저장
		EventHistory eventHistory = EventHistory.builder()
			.eventId(foundEvent.getId())
			.eventItemId(hitEventItem != null ? hitEventItem.getId() : null)
			.title(foundEvent.getTitle())
			.participantId(participant.getId())
			.participantName(participant.getNickname())
			.participantEmail(participant.getEmail())
			.isHit(isHit)
			.build();
		eventHistoryRepository.save(eventHistory);

		// 반환
		if (isHit) {
			return ParticipateEventResponse.builder()
				.result(isHit)
				.code(foundEvent.getCode())
				.resultMessage(foundEvent.getHitMessage())
				.resultImageURL(imageUtil.getFullImageUrl(foundEvent.getHitImageFileName()))
				.itemTitle(hitEventItem.getTitle())
				.itemImageUrl(imageUtil.getFullImageUrl(hitEventItem.getImageFileName()))
				.build();
		}

		return ParticipateEventResponse.builder()
			.result(isHit)
			.code(foundEvent.getCode())
			.resultMessage(foundEvent.getMissMessage())
			.resultImageURL(imageUtil.getFullImageUrl(foundEvent.getMissImageFileName()))
			.itemTitle(null)
			.itemImageUrl(null)
			.build();


	}

	private void validateCanParticipate(Long participantId, Event foundEvent) {
		// 기간 + 현재 상태 체크 후 수정
		fixEventStatus(foundEvent);

		// 이미 참가한 이벤트인지 검증
		checkAlreadyParticipate(participantId);

		// 기간이 지나지는 않았나??
		checkEventTimeOut(foundEvent);

		// 이벤트 상태가 참가 가능한 이벤트 인가??
		checkEventStatus(foundEvent);
	}

	private void fixEventStatus(Event event) {
		LocalDateTime now = LocalDateTime.now();
		EventDateTime eventDateTime = event.getEventDateTime();
		EventStatus eventStatus = event.getStatus();

		// 취소
		if (eventStatus.isCancel()) {
			return;
		}

		// 시작전 -> WAIT
		if (eventDateTime.isBeforeOpenDateTime(now) && eventStatus.isNotWait()) {
			event.changeStatus(EventStatus.WAIT);
		}

		// (종료시간 < now) -> COMPLETE
		if (eventDateTime.isAfterCloseDateTime(now) && eventStatus.isNotComplete()) {
			event.changeStatus(EventStatus.COMPLETE);
		}

		// (시작시간 <= now <= 종료시간) -> PROCESS
		if (eventDateTime.isBeforeOpenDateTime(now) && eventStatus.isNotProcess()) {
			event.changeStatus(EventStatus.PROCESS);
		}
	}

	private void checkAlreadyParticipate(Long participantId) {
		Boolean isAlreadyParticipate = eventHistoryRepository.existsByParticipantId(participantId);
		if (isAlreadyParticipate) {
			throw DuplicatedException.EVENT_HISTORY.get();
		}
	}

	private void checkEventTimeOut(Event event) {
		EventDateTime eventDateTime = event.getEventDateTime();
		LocalDateTime now = LocalDateTime.now();
		if (eventDateTime.isCanNotParticipate(now)) {
			throw EventTimeException.TIME_OUT.get();
		}
	}

	private void checkEventStatus(Event event) {
		EventStatus status = event.getStatus();
		if (status.isNotProcess()) {
			throw EventStatusException.COMPLETE.get();
		}
		if (status.isCancel()) {
			throw EventStatusException.CANCEL.get();
		}
	}

}
