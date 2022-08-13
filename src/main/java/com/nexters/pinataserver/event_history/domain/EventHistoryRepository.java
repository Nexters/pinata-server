package com.nexters.pinataserver.event_history.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {

	Boolean existsByParticipantIdAndEventId(@Param("participantId") Long participantId, @Param("eventId") Long eventId);

	@Query("select eh from EventHistory eh where eh.eventId = :eventId")
	Optional<EventHistory> findByEventId(@Param("eventId") Long eventId);

}
