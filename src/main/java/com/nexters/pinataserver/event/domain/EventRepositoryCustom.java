package com.nexters.pinataserver.event.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.nexters.pinataserver.event.dto.response.ParticipationEventResponse;

public interface EventRepositoryCustom {
	List<Event> getMyEvents(Long userId, Pageable pageable);

	List<ParticipationEventResponse> getParticipationEvents(Long userId, Pageable pageable);
}
