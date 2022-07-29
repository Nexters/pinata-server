package com.nexters.pinataserver.common.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;

@Component
public class AmazonS3Client {

	private final AmazonS3 s3;

	@Value("${ncp.end_point}")
	public String endPoint;

	@Value("${ncp.region_name}")
	public String regionName;

	@Value("${ncp.access_key}")
	public String accessKey;

	@Value("${ncp.secret_key}")
	public String secretKey;

	@Value("${ncp.bucket_name}")
	public String bucketName;

	@Value("${ncp.image_folder_name}")
	public String imageFolderName;

	public AmazonS3Client() {
		s3 = AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
			.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
			.build();
	}

	public String uploadImage(MultipartFile multipartFile, String objectName) {

		try {
			// initialize and get upload ID
			InitiateMultipartUploadResult initiateMultipartUploadResult = s3.initiateMultipartUpload(
				new InitiateMultipartUploadRequest(bucketName, objectName));
			String uploadId = initiateMultipartUploadResult.getUploadId();

			// upload parts
			List<PartETag> partETagList = new ArrayList<PartETag>();

			long contentLength = multipartFile.getResource().contentLength();
			long partSize = multipartFile.getSize();
			String contentType = multipartFile.getContentType();

			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(contentType);
			objectMetadata.setContentLength(contentLength);

			long fileOffset = 0;
			for (int i = 1; fileOffset < contentLength; i++) {
				partSize = Math.min(partSize, (contentLength - fileOffset));

				UploadPartRequest uploadPartRequest = new UploadPartRequest()
					.withBucketName(bucketName)
					.withKey(objectName)
					.withUploadId(uploadId)
					.withPartNumber(i)
					.withInputStream(multipartFile.getInputStream())
					.withFileOffset(fileOffset)
					.withObjectMetadata(objectMetadata)
					.withPartSize(partSize);

				UploadPartResult uploadPartResult = s3.uploadPart(uploadPartRequest);
				partETagList.add(uploadPartResult.getPartETag());

				fileOffset += partSize;
			}

			// complete
			CompleteMultipartUploadResult completeMultipartUploadResult = s3.completeMultipartUpload(
				new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, partETagList));

			return s3.getUrl(bucketName, objectName).toString();

		} catch (SdkClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return null;

	}

}