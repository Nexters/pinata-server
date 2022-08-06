package com.nexters.pinataserver.event.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.nexters.pinataserver.common.exception.ResponseException;
import com.nexters.pinataserver.common.exception.e4xx.EventTimeException;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class EventDateTime {

	@Column(name = "is_period", nullable = false)
	private Boolean isPeriod;

	@Column(name = "open_at")
	private LocalDateTime openAt;

	@Column(name = "close_at")
	private LocalDateTime closeAt;

	@Builder
	public EventDateTime(Boolean isPeriod, LocalDateTime openAt, LocalDateTime closeAt) throws ResponseException {
		validate(isPeriod, openAt, closeAt);
		this.isPeriod = isPeriod;
		this.openAt = openAt;
		this.closeAt = closeAt;
	}

	private void validate(Boolean isPeriod, LocalDateTime openAt, LocalDateTime closeAt) throws ResponseException {
		checkIsPeriod(isPeriod, openAt, closeAt);
		checkOpenCloseDateTimeValidation(openAt, closeAt);
	}

	private void checkOpenCloseDateTimeValidation(LocalDateTime openAt, LocalDateTime closeAt) throws ResponseException {
		if (openAt.isAfter(closeAt)) {
			throw EventTimeException.INVALID_INPUT.get();
		}
	}

	private void checkIsPeriod(Boolean isPeriod, LocalDateTime openAt, LocalDateTime closeAt) throws ResponseException {
		if (isPeriod && (Objects.isNull(openAt) || Objects.isNull(closeAt))) {
			throw EventTimeException.INVALID_INPUT.get();
		}
	}

	public boolean isCanNotParticipate(LocalDateTime now) {
		return now.isAfter(closeAt) || now.isBefore(openAt);
	}

}
