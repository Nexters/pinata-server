package com.nexters.pinataserver.common.domain;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractSoftDeletableEntity extends AbstractDateTimeEntity{

	@Column(name = "use_flag", nullable = false)
	private boolean useFlag = true;

	public void changeUseFlag(boolean useFlag) {
		this.useFlag = useFlag;
	}

}
