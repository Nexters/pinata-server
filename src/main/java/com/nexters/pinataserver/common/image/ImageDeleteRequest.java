package com.nexters.pinataserver.common.image;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageDeleteRequest {

	@NotBlank(message = "imageFileName is not blank")
	private String imageFileName;

}
