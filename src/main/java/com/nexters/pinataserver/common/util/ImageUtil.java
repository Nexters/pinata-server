package com.nexters.pinataserver.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUtil {

	@Value("${cloud.aws.s3.bucket}")
	private String bucketPath;

	public String extractImageFileName(String imageUrl) {
		return imageUrl.replace(bucketPath, "");
	}

	public String getFullImageUrl(String imageFileName) {
		return bucketPath.concat(imageFileName);
	}

}
