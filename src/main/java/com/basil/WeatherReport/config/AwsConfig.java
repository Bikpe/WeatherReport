// src/main/java/com/phoenixtype/WeatherReport/config/AwsConfig.java

package com.basil.WeatherReport.config;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Bean
    public S3Client s3Client() {
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create("your_access_key_id", "your_secret_access_key");
        return S3Client.builder()
                .region(Region.US_EAST_1)
//                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
}