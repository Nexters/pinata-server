package com.nexters.pinataserver.user.domain;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nexters.pinataserver.common.domain.AbstractDateTimeEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends AbstractDateTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long providerId;

	private String email;

	private String nickname;

	private String profileImageUrl;

	@Convert(converter = UserStateConverter.class)
	private UserState state;

}
