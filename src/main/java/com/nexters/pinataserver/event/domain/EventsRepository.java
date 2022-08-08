package com.nexters.pinataserver.event.domain;

import static com.nexters.pinataserver.event.domain.QEvent.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class EventsRepository {

	private final JPAQueryFactory queryFactory;

	public Page<Event> getMyEvents(Long userId, Pageable pageable) {
		List<Event> events = queryFactory
			.selectFrom(event)
			.where(event.organizerId.eq(userId))
			.orderBy(event.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return new PageImpl<>(events, pageable, events.size());
	}

}
