package com.teju.greenwoods.infrastructure.storage;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.teju.greenwoods.application.exception.StorageException;
import com.teju.greenwoods.application.service.FileStorage;

import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Component
public class S3FileStorage implements FileStorage {

    private static final Logger log = LoggerFactory.getLogger(S3FileStorage.class);
    private static final String UNAVAILABLE = "File storage is not available right now";

    private final S3Client s3Client;
    private final S3Presigner presigner;
    private final String bucket;

    public S3FileStorage(S3Client s3Client,
                         S3Presigner presigner,
                         @Value("${aws.s3.bucket-name}") String bucket) {
        this.s3Client = s3Client;
        this.presigner = presigner;
        this.bucket = bucket;
    }

    @Override
    public void put(String key, byte[] content, String contentType) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(content));
        } catch (SdkException e) {
            log.error("S3 upload failed for key {}: {}", key, e.getMessage());
            throw new StorageException(UNAVAILABLE);
        }
    }

    @Override
    public void delete(String key) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build());
        } catch (SdkException e) {
            log.error("S3 delete failed for key {}: {}", key, e.getMessage());
            throw new StorageException(UNAVAILABLE);
        }
    }

    @Override
    public String presignedGetUrl(String key, Duration validFor) {
        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(validFor)
                    .getObjectRequest(getRequest)
                    .build();
            return presigner.presignGetObject(presignRequest).url().toString();
        } catch (SdkException e) {
            log.error("Could not create a link for key {}: {}", key, e.getMessage());
            throw new StorageException(UNAVAILABLE);
        }
    }
}