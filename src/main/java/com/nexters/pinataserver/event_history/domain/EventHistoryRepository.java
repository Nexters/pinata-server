package com.nexters.pinataserver.event_history.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {

//	@Query("select eh.id from EventHistory eh where eh.participantId = :participantId and eh.eventId = :eventId")
	Boolean existsByParticipantIdAndEventId(@Param("participantId") Long participantId, @Param("eventId") Long eventId);

}
