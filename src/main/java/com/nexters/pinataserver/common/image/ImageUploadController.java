package com.nexters.pinataserver.common.image;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImageUploadController {

	// @Value("${cloud.aws.s3.bucket_path}")
	// private String BUCKET_ADDRESS;

	private final ImageUploadService imageUploadService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/api/v1/images")
	public CommonApiResponse createBoards(@RequestParam(value = "files", required = false) MultipartFile multipartFile) throws
		IOException {
		String uploadedUrl = imageUploadService.upload(multipartFile);

		return CommonApiResponse.ok(ImageUploadResponse.of(uploadedUrl));
	}

}