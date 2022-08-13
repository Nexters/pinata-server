package com.nexters.pinataserver.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUtil {

	@Value("${cloud.aws.s3.bucket_path}")
	private static String bucketPath;

	public static String extractImageFileName(String imageUrl) {
		return imageUrl.replace(bucketPath, "");
	}

	public static String getFullImageUrl(String imageFileName) {
		return bucketPath.concat(imageFileName);
	}

}
