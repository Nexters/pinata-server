package com.nexters.pinataserver.common.image;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUploadResponse {

	private List<String> imageUrls;

	public static ImageUploadResponse of(List<String> imageUrls) {
		ImageUploadResponse imageUploadResponse = new ImageUploadResponse();
		imageUploadResponse.setImageUrls(imageUrls);

		return imageUploadResponse;
	}

}