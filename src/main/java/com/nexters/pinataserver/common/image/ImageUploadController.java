package com.nexters.pinataserver.common.image;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexters.pinataserver.common.dto.response.CommonApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageUploadController {

	private final ImageUploadService imageUploadService;

	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public CommonApiResponse<ImageUploadResponse> upload(
		@RequestParam(value = "files", required = false) List<MultipartFile> multipartFile) {
		List<String> uploadedUrls = imageUploadService.upload(multipartFile);

		return CommonApiResponse.ok(ImageUploadResponse.of(uploadedUrls));
	}


	@GetMapping(value = "/download/{fileName}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName) {
		byte[] imageData =  imageUploadService.downloadFile(fileName);
		return ResponseEntity.ok()
			.contentType(contentType(fileName))
			.body(imageData);
	}
	private MediaType contentType(String filename) {
		String[] fileArrSplit = filename.split("\\.");
		String fileExtension = fileArrSplit[fileArrSplit.length - 1];
		switch (fileExtension) {
			case "txt":
				return MediaType.TEXT_PLAIN;
			case "png":
				return MediaType.IMAGE_PNG;
			case "jpg":
			case "svg":
			case "jpeg":
				return MediaType.IMAGE_JPEG;
			default:
				return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

	@DeleteMapping
	public CommonApiResponse delete(@RequestBody ImageDeleteRequest request) {

		imageUploadService.deleteObject(request.getImageFileName());

		return CommonApiResponse.ok(null);
	}

}
