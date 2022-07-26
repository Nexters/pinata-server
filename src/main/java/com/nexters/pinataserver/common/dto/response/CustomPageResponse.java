package com.nexters.pinataserver.common.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CustomPageResponse<T> {

	private Long totalCount;

	private int pageIndex;

	private int pageSize;

	private List<T> list;

	private boolean hasNext;

	protected CustomPageResponse(Page<T> tPage) {
		this.totalCount = tPage.getTotalElements();
		this.pageIndex = tPage.getNumber();
		this.pageSize = tPage.getSize();
		this.list = tPage.getContent();
		this.hasNext = tPage.hasNext();
	}

}
