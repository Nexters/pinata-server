package com.nexters.pinataserver.user.domain;

public enum UserState {

	ACTIVE(1), WITHDRAW(2);

	public int state;

	UserState(int state) {
		this.state = state;
	}
}
