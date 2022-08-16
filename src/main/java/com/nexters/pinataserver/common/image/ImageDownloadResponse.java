package com.nexters.pinataserver.common.image;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageDownloadResponse {

	private byte[] bytes;

	public static ImageDownloadResponse of(byte[] bytes) {
		ImageDownloadResponse imageDownloadResponse = new ImageDownloadResponse();
		imageDownloadResponse.setBytes(bytes);

		return imageDownloadResponse;
	}

}
