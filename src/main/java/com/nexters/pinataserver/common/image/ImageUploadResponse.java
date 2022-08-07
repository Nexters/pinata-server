package com.nexters.pinataserver.common.image;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUploadResponse {

	private String imageUrl;

	public static ImageUploadResponse of(String imageUrl) {
		ImageUploadResponse imageUploadResponse = new ImageUploadResponse();
		imageUploadResponse.setImageUrl(imageUrl);

		return imageUploadResponse;
	}

}