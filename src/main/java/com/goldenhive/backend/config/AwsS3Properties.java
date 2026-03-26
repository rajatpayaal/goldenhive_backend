package com.goldenhive.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aws")
public class AwsS3Properties {
    private String region;
    private final S3 s3 = new S3();

    @Data
    public static class S3 {
        private String bucket;
    }
}
