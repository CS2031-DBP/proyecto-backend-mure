package dbp.proyecto.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3ClientConfig {
    @Value("${amazonS3.accessKey}")
    private String accessKey;

    @Value("${amazonS3.secretKey}")
    private String secretKey;

    @Value("${amazonS3.region}")
    private String region;

    @Bean
    public AmazonS3 getAmazonS3Client() {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey, "523690075384");

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(String.valueOf(RegionUtils.getRegion(region)))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}
