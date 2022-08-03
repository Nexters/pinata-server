package com.nexters.pinataserver.event.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

	Optional<Event> findByCode(@Param("code") String eventCode);

}
