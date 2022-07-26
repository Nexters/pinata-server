package com.nexters.pinataserver.event_history.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tb_event_histories")
@Where(clause = "use_flag = true")
public class EventHistory extends AbstractSoftDeletableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "event_id", nullable = false)
	private Long eventId;

	@Column(name = "title", length = 500, nullable = false)
	private String title;

	@Column(name = "participant_id", nullable = false)
	private Long participantId;

	@Column(name = "participant_email", nullable = false)
	private String participantEmail;

	@Column(name = "participant_name", length = 100, nullable = false)
	private String participantName;

	@Column(name = "is_hit", nullable = false)
	private boolean isHit;

	@Column(name = "event_item_id", nullable = false)
	private Long eventItemId;

	@Builder
	private EventHistory(
		Long id,
		Long eventId,
		String title,
		Long participantId,
		String participantEmail,
		String participantName,
		boolean isHit,
		Long eventItemId
	) {
		this.id = id;
		this.eventId = eventId;
		this.title = title;
		this.participantId = participantId;
		this.participantEmail = participantEmail;
		this.participantName = participantName;
		this.isHit = isHit;
		this.eventItemId = eventItemId;
	}

}
