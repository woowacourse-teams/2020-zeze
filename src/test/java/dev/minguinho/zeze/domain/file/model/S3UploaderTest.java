package dev.minguinho.zeze.domain.file.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import io.findify.s3mock.S3Mock;

import dev.minguinho.zeze.domain.file.exception.FileNotConvertedException;

class S3UploaderTest {
    private static S3Mock s3Mock;
    private S3Uploader s3Uploader;
    private String bucket;
    private String directory;

    @BeforeAll
    static void beforeAll() {
        s3Mock = new S3Mock.Builder()
            .withPort(8001)
            .withInMemoryBackend()
            .build();
        s3Mock.start();
    }

    @AfterAll
    static void afterAll() {
        s3Mock.stop();
    }

    @BeforeEach
    void setUp() throws Exception {
        this.bucket = "markdwon-ppt-test";
        this.directory = "static";

        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
            "http://localhost:8001", "ap-northeast-2");
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
            .withPathStyleAccessEnabled(true)
            .withEndpointConfiguration(endpoint)
            .build();
        amazonS3.createBucket(bucket);

        this.s3Uploader = new S3Uploader(amazonS3);

        Field bucketField = S3Uploader.class.getDeclaredField("bucket");
        bucketField.setAccessible(true);
        bucketField.set(s3Uploader, bucket);
        Field directoryField = S3Uploader.class.getDeclaredField("directory");
        directoryField.setAccessible(true);
        directoryField.set(s3Uploader, directory);
    }

    @Test
    @DisplayName("S3에 파일 업로드")
    void upload() {
        String fileName = "test-image.png";
        MultipartFile multipartFile = new MockMultipartFile("files", fileName,
            MediaType.IMAGE_PNG_VALUE, "test-data".getBytes());
        String actual = s3Uploader.upload(multipartFile);
        String basicUrl = String.format("http://localhost:8001/%s/%s", bucket,
            directory);

        assertAll(
            () -> assertThat(actual).startsWith(basicUrl),
            () -> assertThat(actual).contains(fileName)
        );
    }

    @Test
    @DisplayName("파일 변환이 안되는 경우")
    void uploadWithInvalidMultipartFile() {
        MultipartFile multipartFile = new MockMultipartFile("files", (byte[])null);

        assertThatThrownBy(() -> s3Uploader.upload(multipartFile))
            .isInstanceOf(FileNotConvertedException.class)
            .hasMessage("의 파일 변환에 실패했습니다.");
    }
}
