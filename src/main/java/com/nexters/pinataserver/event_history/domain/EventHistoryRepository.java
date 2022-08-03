package com.nexters.pinataserver.event_history.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {

	Boolean existsByParticipantId(@Param("participantId") Long participantId);

}
