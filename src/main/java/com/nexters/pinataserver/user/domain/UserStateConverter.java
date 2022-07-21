package com.nexters.pinataserver.user.domain;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserStateConverter implements AttributeConverter<UserState, Integer> {

	@Override
	public Integer convertToDatabaseColumn(UserState attribute) {
		return attribute.state;
	}

	@Override
	public UserState convertToEntityAttribute(Integer dbData) {
		return Stream.of(UserState.values())
			.filter(x -> x.state == dbData)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
