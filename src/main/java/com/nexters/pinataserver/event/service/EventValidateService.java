package com.nexters.pinataserver.event.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.pinataserver.common.exception.e4xx.DuplicatedException;
import com.nexters.pinataserver.common.exception.e4xx.EventStatusException;
import com.nexters.pinataserver.common.exception.e4xx.EventTimeException;
import com.nexters.pinataserver.event.domain.Event;
import com.nexters.pinataserver.event.domain.EventDateTime;
import com.nexters.pinataserver.event.domain.EventStatus;
import com.nexters.pinataserver.event_history.domain.EventHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventValidateService {

    private final EventHistoryRepository eventHistoryRepository;

    @Transactional
    public void validateCanParticipate(Long participantId, Event foundEvent) {

        // 이미 참가한 이벤트인지 검증
        checkAlreadyParticipate(participantId, foundEvent.getId());

        // 기간이 지나지는 않았나??
        checkEventTimeOut(foundEvent);

        // 이벤트 상태가 참가 가능한 이벤트 인가??
        checkEventStatus(foundEvent);
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

        // TODO : 이벤트 참가와 이벤트 조회 시 이부분이 달라져야함... ( 참가에서는 필요, 조회에서는 불필요 ) 일단은 주석처리..
        // if (eventDateTime.isBeforeOpenDateTime(now)) {
        //     throw EventTimeException.TIME_OUT.get();
        // }

        if (eventDateTime.isCanNotParticipate(now)) {
            throw EventTimeException.TIME_OUT.get();
        }
    }

    private void checkEventStatus(Event event) {
        EventStatus status = event.getStatus();
        // // TODO : 이벤트 시작 전에 대한 내용 ( 이벤트 개설시 상태관리가 아직 적용되지 않아서 보류 )
        // if (status.isWait()) {
        //     throw EventStatusException.WAIT.get();
        // }

        if (status.isComplete()) {
            throw EventStatusException.COMPLETE.get();
        }
        if (status.isCancel()) {
            throw EventStatusException.CANCEL.get();
        }
    }

}
