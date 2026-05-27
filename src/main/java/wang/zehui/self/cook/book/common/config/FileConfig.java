package wang.zehui.self.cook.book.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

import java.net.URI;

/**
 * @Author wangzehui
 * @Date 2026/5/27 11:46
 */
@Configuration
@Slf4j
public class FileConfig {

    @Value("${file.rustfs.endpoint}")
    private String endpoint;

    @Value("${file.rustfs.accessKeyId}")
    private String accessKeyId;

    @Value("${file.rustfs.secretAccessKey}")
    private String secretAccessKey;

    @Value("${file.rustfs.bucketName:self-cook}")
    private String bucketName;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        S3Client s3Client = S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                // 地区随便写，rustfs不校验
                .region(Region.CA_CENTRAL_1)
                .credentialsProvider(staticCredentialsProvider)
                .forcePathStyle(true)
                .build();

        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
        } catch (NoSuchBucketException e) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        } catch (AwsServiceException e) {
            // 处理其他异常（如权限不足、网络问题）
            log.error("Failed to verify/create bucket: {}", bucketName, e);
            throw new RuntimeException("初始化创建bucket失败", e);
        }

        return s3Client;
    }
}

