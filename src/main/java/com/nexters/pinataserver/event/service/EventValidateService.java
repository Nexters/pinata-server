package com.nexters.pinataserver.event.service;

import com.nexters.pinataserver.common.exception.e4xx.DuplicatedException;
import com.nexters.pinataserver.common.exception.e4xx.EventStatusException;
import com.nexters.pinataserver.common.exception.e4xx.EventTimeException;
import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventDateTime;
import com.nexters.pinataserver.event.domain.EventStatus;
import com.nexters.pinataserver.event_history.domain.EventHistoryRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventValidateService {

    private final EventHistoryRepository eventHistoryRepository;

    public void validateCanParticipate(Long participantId, Event foundEvent) {
        // 기간 + 현재 상태 체크 후 수정
        fixEventStatus(foundEvent);

        // 이미 참가한 이벤트인지 검증
        checkAlreadyParticipate(participantId, foundEvent.getId());

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

    private void checkAlreadyParticipate(Long participantId, Long eventId) {
        Boolean isAlreadyParticipate = eventHistoryRepository.existsByParticipantIdAndEventId(participantId, eventId);
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
        if (status.isComplete()) {
            throw EventStatusException.COMPLETE.get();
        }
        if (status.isCancel()) {
            throw EventStatusException.CANCEL.get();
        }
    }

}
