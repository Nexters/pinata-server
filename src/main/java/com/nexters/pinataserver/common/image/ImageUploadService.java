package com.nexters.pinataserver.common.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.nexters.pinataserver.common.exception.e5xx.FileDownloadException;
import com.nexters.pinataserver.common.exception.e5xx.FileUploadException;
import com.nexters.pinataserver.common.util.ImageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageUploadService {

	private final AmazonS3Client amazonS3Client;

	private final ImageUtil imageUtil;

	@Value("${cloud.aws.s3.bucket}")
	public String bucket;  // S3 버킷 이름

	public List<String> upload(List<MultipartFile> multipartFiles) {

		List<String> fileNameList = new ArrayList<>();

		multipartFiles.forEach(file -> {
			String fileName = createFileName(file.getOriginalFilename());
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());
			objectMetadata.setContentType(file.getContentType());

			try (InputStream inputStream = file.getInputStream()) {
				amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			} catch (IOException e) {
				throw FileUploadException.IMAGE.get();
			}

			fileNameList.add(imageUtil.getFullImageUrl(fileName));
		});

		return fileNameList;
	}

	private String createFileName(String fileName) {
		return UUID.randomUUID().toString().concat(getFileExtension(fileName));
	}

	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (StringIndexOutOfBoundsException e) {
			throw FileUploadException.IMAGE_FORMAT.get();
		}
	}

	public void deleteObject(String imageFileName) throws AmazonServiceException {

		amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, imageFileName));

	}

	public byte[] downloadFile(String imageFileName) {
		try {
			S3Object s3object = amazonS3Client.getObject(new GetObjectRequest(bucket, imageFileName));

			S3ObjectInputStream objectInputStream = s3object.getObjectContent();

			return IOUtils.toByteArray(objectInputStream);
		} catch (IOException ioException) {
			log.error("IOException: " + ioException.getMessage());
			throw FileDownloadException.IMAGE.get();
		} catch (AmazonServiceException serviceException) {
			log.info("AmazonServiceException Message:    " + serviceException.getMessage());
			throw FileDownloadException.IMAGE.get();
		} catch (AmazonClientException clientException) {
			log.info("AmazonClientException Message: " + clientException.getMessage());
			throw FileDownloadException.IMAGE.get();
		}
		// catch (SerialException serialException) {
		// 	log.error("SerialException Message: " + serialException.getMessage());
		// 	throw FileDownloadException.IMAGE.get();
		// } catch (SQLException sqlException) {
		// 	log.error("SQLException Message: " + sqlException.getMessage());
		// 	throw FileDownloadException.IMAGE.get();
		// }
	}

	// public Resource downloadFile(String imageFileName) {
	// 	try {
	// 		S3Object s3object = amazonS3Client.getObject(new GetObjectRequest(bucket, imageFileName));
	//
	// 		S3ObjectInputStream objectInputStream = s3object.getObjectContent();
	// 		byte[] bytes = IOUtils.toByteArray(objectInputStream);
	//
	// 		return new ByteArrayResource(bytes);
	// 	} catch (IOException ioException) {
	// 		log.error("IOException: " + ioException.getMessage());
	// 		throw FileDownloadException.IMAGE.get();
	// 	} catch (AmazonServiceException serviceException) {
	// 		log.info("AmazonServiceException Message:    " + serviceException.getMessage());
	// 		throw FileDownloadException.IMAGE.get();
	// 	} catch (AmazonClientException clientException) {
	// 		log.info("AmazonClientException Message: " + clientException.getMessage());
	// 		throw FileDownloadException.IMAGE.get();
	// 	}
	// }
	//
	// public Blob downloadFile(String imageFileName) {
	// 	try {
	// 		S3Object s3object = amazonS3Client.getObject(new GetObjectRequest(bucket, imageFileName));
	//
	// 		S3ObjectInputStream objectInputStream = s3object.getObjectContent();
	// 		byte[] bytes = IOUtils.toByteArray(objectInputStream);
	//
	// 		return new SerialBlob(bytes);
	// 	} catch (IOException ioException) {
	// 		log.error("IOException: " + ioException.getMessage());
	// 		throw FileDownloadException.IMAGE.get();
	// 	} catch (AmazonServiceException serviceException) {
	// 		log.error("AmazonServiceException Message:    " + serviceException.getMessage());
	// 		throw FileDownloadException.IMAGE.get();
	// 	} catch (AmazonClientException clientException) {
	// 		log.error("AmazonClientException Message: " + clientException.getMessage());
	// 		throw FileDownloadException.IMAGE.get();
	// 	} catch (SerialException serialException) {
	// 		log.error("SerialException Message: " + serialException.getMessage());
	// 		throw FileDownloadException.IMAGE.get();
	// 	} catch (SQLException sqlException) {
	// 		log.error("SQLException Message: " + sqlException.getMessage());
	// 		throw FileDownloadException.IMAGE.get();
	// 	}
	// }

}

