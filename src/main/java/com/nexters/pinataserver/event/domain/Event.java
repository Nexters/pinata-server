package com.nexters.pinataserver.event.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Builder
@Table(name = "tb_events")
@Where(clause = "use_flag = true")
public class Event extends AbstractSoftDeletableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "code", length = 500, nullable = false)
	private String code;

	@Column(name = "title", length = 500, nullable = false)
	private String title;

	@Column(name = "open_at")
	private LocalDateTime openAt;

	@Column(name = "close_at")
	private LocalDateTime closeAt;

	@Enumerated(EnumType.STRING)
	private EventType type;


	@Enumerated(EnumType.STRING)
	private EventStatus status;

	@Column(name = "limit_count", nullable = false)
	private Integer limitCount;

	@Column(name = "hit_count", nullable = false)
	private Integer hitCount;

	@Column(name = "participant_count", nullable = false)
	private Integer participantCount;

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

	private Event(
		Long id,
		String code,
		String title,
		LocalDateTime openAt,
		LocalDateTime closeAt,
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
		this.code = code;
		this.title = title;
		this.openAt = openAt;
		this.closeAt = closeAt;
		this.type = type;
		this.status = status;
		this.limitCount = limitCount;
		this.hitCount = hitCount;
		this.participantCount = participantCount;
		this.hitImageFileName = hitImageFileName;
		this.hitMessage = hitMessage;
		this.missImageFileName = missImageFileName;
		this.missMessage = missMessage;

		if (Objects.nonNull(eventItems) && !eventItems.isEmpty()) {
			eventItems.forEach(this::addEventItem);
		}
	}

	public void addEventItem(EventItem eventItem) {
		eventItem.changeEvent(this);
	}

}
