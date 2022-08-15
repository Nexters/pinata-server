package com.nexters.pinataserver.event.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nexters.pinataserver.common.validation.ValidEnum;
import com.nexters.pinataserver.event.domain.EventType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterEventRequest {

	@NotBlank(message = "title is not blank")
	private String title;

	@NotNull(message = "isPeriod is not null")
	private boolean isPeriod;

	// @Pattern(regexp = "^(19[0-9]{2}|2[0-9]{3})-(0[1-9]|1[012])-([123]0|[012][1-9]|31) ([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", message = "openAt must be HH:mm yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime openAt;

	@NotNull(message = "closeAt is not null")
	// @Pattern(regexp = "^(19[0-9]{2}|2[0-9]{3})-(0[1-9]|1[012])-([123]0|[012][1-9]|31) ([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", message = "openAt must be HH:mm yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime closeAt;

	@ValidEnum(enumClass = EventType.class)
	private EventType type;

	@Valid
	@Size(min = 1, message = "items must be more than 1")
	private List<EventItemParam> items;

	@NotBlank(message = "hitMessage is not blank")
	private String hitMessage;

	@NotBlank(message = "hitImageUrl is not blank")
	private String hitImageUrl;

	@NotBlank(message = "missMessage is not blank")
	private String missMessage;

	@NotBlank(message = "missImageUrl is not blank")
	private String missImageUrl;

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class EventItemParam {

		@NotBlank(message = "title is not blank")
		private String title;

		private String imageUrl;

		@Positive(message = "rank must be positive")
		private Integer rank;

	}

}
