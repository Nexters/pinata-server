package com.nexters.pinataserver.event.domain;

public enum EventStatus {
	PROCESS, WAIT, COMPLETE, CANCEL;

	public boolean isComplete() {
		return this == COMPLETE;
	}

	public boolean isCancel() {
		return this == CANCEL;
	}

	public boolean isNotWait() {
		return this != WAIT;
	}

	public boolean isNotProcess() {
		return this != PROCESS;
	}

	public boolean isNotComplete() {
		return this != COMPLETE;
	}

}
