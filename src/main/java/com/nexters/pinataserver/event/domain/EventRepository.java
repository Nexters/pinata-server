package com.nexters.pinataserver.event.domain;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

	@EntityGraph(attributePaths = {"eventItems"})
	Optional<Event> findByCode(@Param("code") String eventCode);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@EntityGraph(attributePaths = {"eventItems"})
	@Query("select e from Event e where e.code = :code")
	Optional<Event> findByCodeForUpdate(@Param("code") String eventCode);

}
