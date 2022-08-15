package com.nexters.pinataserver.event.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_event_items")
@Where(clause = "use_flag = true")
public class EventItem extends AbstractSoftDeletableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title", length = 500, nullable = false)
	private String title;

	@Column(name = "image_file_name", length = 100)
	private String imageFileName;

	@Column(name = "ranking", nullable = false)
	private Integer ranking;

	@Column(name = "is_accepted", nullable = false)
	private boolean isAccepted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "fk_event_item_to_event"))
	private Event event;

	@Builder
	private EventItem(
		Long id,
		String title,
		String imageFileName,
		Integer ranking,
		boolean isAccepted,
		Event event
	) {
		this.id = id;
		this.title = title;
		this.imageFileName = imageFileName;
		this.ranking = ranking;
		this.isAccepted = isAccepted;
		this.event = event;
	}

	public void changeEvent(Event event) {
		if (Objects.nonNull(event)) {
			event.getEventItems().remove(this);
			event.getEventItems().add(this);
		}
		this.event = event;
	}

	public void accept() {
		this.isAccepted = true;
	}
}
