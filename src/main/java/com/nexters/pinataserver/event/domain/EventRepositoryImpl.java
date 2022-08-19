package com.nexters.pinataserver.event.domain;

import static com.nexters.pinataserver.event.domain.QEvent.*;
import static com.nexters.pinataserver.event.domain.QEventItem.*;
import static com.nexters.pinataserver.event_history.domain.QEventHistory.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.nexters.pinataserver.event.dto.query.ParticipationEventDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Event> getMyEvents(Long userId, Pageable pageable) {
		return queryFactory
			.selectFrom(event)
			.where(event.organizerId.eq(userId))
			.orderBy(event.status.asc(), event.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
	}

	@Override
	public List<ParticipationEventDto> getParticipationEvents(Long userId, Pageable pageable) {
		return queryFactory.from(eventHistory)
			.select(Projections.constructor(
				ParticipationEventDto.class,
				event.id.as("eventId"),
				event.code.as("eventCode"),
				event.title.as("eventTitle"),
				eventHistory.isHit.as("result"),
				event.hitMessage.as("hitMessage"),
				event.hitImageFileName.as("hitImageFileName"),
				event.missMessage.as("missMessage"),
				event.missImageFileName.as("missImageFileName"),
				eventItem.id.as("itemId"),
				eventItem.title.as("itemTitle"),
				eventItem.imageFileName.as("itemImageFileName"),
				event.eventDateTime.isPeriod,
				event.eventDateTime.openAt,
				event.eventDateTime.closeAt,
				event.createdAt.as("participateAt")
			))
			.leftJoin(event).on(event.id.eq(eventHistory.eventId))
			.leftJoin(eventItem).on(eventItem.id.eq(eventHistory.eventItemId))
			.where(eventHistory.participantId.eq(userId))
			.orderBy(event.status.asc(), event.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
	}

	// @Override
	// public List<ReadEventResponse.EventItemResult> getEventResult(Long eventId) {
	// 	return queryFactory.from(event)
	// 		.select(Projections.constructor(
	// 			ReadEventResponse.EventItemResult.class,
	// 			eventHistory.id.as("id"),
	// 			eventHistory.title.as("title"),
	// 			user.profileImageUrl.as("imageUrl"),
	// 			eventHistory.useFlag.as("isAccepted"),
	// 			eventHistory.participantEmail.as("acceptorEmail"),
	// 			eventHistory.participantName.as("acceptorNickname"),
	// 			user.profileImageUrl.as("acceptorProfileImageUrl")
	// 		))
	// 		.leftJoin(eventHistory).on(eventHistory.eventId.eq(eventId))
	// 		.leftJoin(user).on(user.id.eq(eventHistory.participantId))
	// 		.fetch();
	// }

}
