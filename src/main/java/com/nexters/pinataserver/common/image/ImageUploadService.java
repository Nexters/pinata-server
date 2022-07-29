package com.nexters.pinataserver.common.image;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageUploadService {

	private final AmazonS3Client amazonS3Client;

	public String upload(MultipartFile multipartFile) {
		String objectName = makeObjectName(multipartFile);

		return amazonS3Client.uploadImage(multipartFile, objectName);
	}

	private String makeObjectName(MultipartFile multipartFile) {
		String objectName = new StringBuilder(UUID.randomUUID().toString())
			.append("-")
			.append(multipartFile.getOriginalFilename())
			.toString();

		return objectName;
	}

}