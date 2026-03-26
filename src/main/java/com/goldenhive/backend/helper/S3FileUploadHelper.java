package com.goldenhive.backend.helper;

import com.goldenhive.backend.config.AwsS3Properties;
import com.goldenhive.backend.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3FileUploadHelper {

    private final S3Client s3Client;
    private final AwsS3Properties awsS3Properties;

    public List<String> uploadFiles(List<MultipartFile> files, String folder) {
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        return files.stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(file -> uploadFile(file, folder))
                .toList();
    }

    public String uploadFile(MultipartFile file, String folder) {
        try {
            String extension = extractExtension(file.getOriginalFilename());
            String key = folder + "/" + resolveMediaFolder(file.getContentType()) + "/" + UUID.randomUUID() + extension;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsS3Properties.getS3().getBucket())
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return s3Client.utilities()
                    .getUrl(builder -> builder.bucket(awsS3Properties.getS3().getBucket()).key(key))
                    .toExternalForm();
        } catch (IOException ex) {
            throw new BadRequestException("Failed to read uploaded file: " + file.getOriginalFilename());
        } catch (Exception ex) {
            throw new BadRequestException("Failed to upload file to S3: " + file.getOriginalFilename());
        }
    }

    private String extractExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    private String resolveMediaFolder(String contentType) {
        if (contentType == null || contentType.isBlank()) {
            return "files";
        }
        if (contentType.startsWith("image/")) {
            return "images";
        }
        if (contentType.startsWith("video/")) {
            return "videos";
        }
        return "files";
    }
}
