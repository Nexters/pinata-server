package com.nexters.pinataserver.common.dto.request;

import org.springframework.data.domain.PageRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CustomPageRequest {

	private int pageIndex;

	private int size;

	public PageRequest convertToPageRequest() {
		return PageRequest.of(pageIndex, size);
	}

}

