package com.nexters.pinataserver.common.image;

import java.sql.Blob;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageDownloadResponse {

	private Blob imageData;

	public static ImageDownloadResponse of(Blob imageData) {
		ImageDownloadResponse imageDownloadResponse = new ImageDownloadResponse();
		imageDownloadResponse.setImageData(imageData);

		return imageDownloadResponse;
	}

}
