package com.nexters.pinataserver.event.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.pinataserver.common.exception.e4xx.NotFoundException;
import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventDateTime;
import com.nexters.pinataserver.event.domain.EventRepository;
import com.nexters.pinataserver.event.domain.EventStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class FixStatusService {

	private final EventRepository eventRepository;

	public void fixEventStatus(String eventCode) {
		// 이벤트 조회
		Event event = eventRepository.findByCodeForUpdate(eventCode)
			.orElseThrow(NotFoundException.EVENT);

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

}
