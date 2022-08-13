package com.nexters.pinataserver.event.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.nexters.pinataserver.common.domain.AbstractSoftDeletableEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_events")
@Where(clause = "use_flag = true")
@Builder
public class Event extends AbstractSoftDeletableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "organizer_id", nullable = false)
	private Long organizerId;

	@Column(name = "code", length = 500, nullable = false)
	private String code;

	@Column(name = "title", length = 500, nullable = false)
	private String title;

	@Embedded
	private EventDateTime eventDateTime;

	@Enumerated(EnumType.STRING)
	private EventType type;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private EventStatus status = EventStatus.WAIT;

	@Column(name = "limit_count", nullable = false)
	private Integer limitCount;

	@Builder.Default
	@Column(name = "hit_count", nullable = false)
	private Integer hitCount = 0;

	@Builder.Default
	@Column(name = "participant_count", nullable = false)
	private Integer participantCount = 0;

	@Column(name = "hit_image_file_name", length = 100)
	private String hitImageFileName;

	@Column(name = "hit_message", length = 1000)
	private String hitMessage;

	@Column(name = "miss_image_file_name", length = 100)
	private String missImageFileName;

	@Column(name = "miss_message", length = 1000)
	private String missMessage;

	@Builder.Default
	@OrderBy("ranking ASC")
	@OneToMany(
		mappedBy = "event",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<EventItem> eventItems = new ArrayList<>();

	public Event(
		Long id,
		Long organizerId,
		String code,
		String title,
		EventDateTime eventDateTime,
		EventType type,
		EventStatus status,
		Integer limitCount,
		Integer hitCount,
		Integer participantCount,
		String hitImageFileName,
		String hitMessage,
		String missImageFileName,
		String missMessage,
		List<EventItem> eventItems
	) {
		this.id = id;
		this.organizerId = organizerId;
		this.code = code;
		this.title = title;
		this.eventDateTime = eventDateTime;
		this.type = type;
		this.status = status;
		this.limitCount = limitCount;
		this.hitCount = hitCount;
		this.participantCount = participantCount;
		this.hitImageFileName = hitImageFileName;
		this.hitMessage = hitMessage;
		this.missImageFileName = missImageFileName;
		this.missMessage = missMessage;

		this.eventItems = new ArrayList<>();
		if (Objects.nonNull(eventItems) && !eventItems.isEmpty()) {
			eventItems.forEach(this::addEventItem);
		}
	}

	public void addEventItem(EventItem eventItem) {
		eventItem.changeEvent(this);
	}

	public void changeStatus(EventStatus eventStatus) {
		this.status = eventStatus;
	}

	public void hit() {
		hitCount++;
		participantCount++;
	}

	public void miss() {
		participantCount++;
	}

	public EventItem getHitEventItem() {
		return eventItems.stream().filter(eventItem -> !eventItem.isAccepted())
			.findFirst()
			.orElse(null);
	}

	public boolean isPossibleHit() {
		return hitCount < limitCount;
	}

}
