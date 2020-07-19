package dev.minguinho.zeze.aws;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

class S3UploaderTest {
    private String bucket;
    private String directory;
    private AmazonS3 amazonS3;
    private S3Uploader s3Uploader;

    @BeforeEach
    void setUp() {
        this.bucket = "markdown-ppt-test";
        this.directory = "static";
        this.amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.AP_NORTHEAST_2)
            .build();
        this.s3Uploader = new S3Uploader(bucket, directory, amazonS3);
    }

    @Test
    @DisplayName("S3에 파일 업로드")
    void upload() {
        String filePath = "src/test/resources";
        String fileName = "test-image.png";
        File file = new File(String.format("%s/%s", filePath, fileName));
        String basicURL = String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s", bucket, directory);
        String actual = s3Uploader.upload(file);

        assertAll(
            () -> assertThat(actual).startsWith(basicURL),
            () -> assertThat(actual).contains(fileName)
        );

        amazonS3.deleteObject(bucket, actual.substring(basicURL.length() - directory.length()));
    }
}
