package dev.minguinho.zeze.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import dev.minguinho.zeze.aws.S3Uploader;

@ExtendWith(MockitoExtension.class)
class PresentationServiceTest {
    private PresentationService presentationService;

    @Mock
    private S3Uploader s3Uploader;

    @BeforeEach
    void setUp() {
        presentationService = new PresentationService(s3Uploader);
    }

    @Test
    @DisplayName("프레젠테이션에 있는 파일 업로드")
    void upload() {
        MultipartFile multipartFile = new MockMultipartFile("test-image.png", (byte[])null);
        String expected = "url";
        when(s3Uploader.upload(multipartFile)).thenReturn(expected);

        assertThat(presentationService.upload(Collections.singletonList(multipartFile)).getUrls().get(0)).isEqualTo(
            expected);
    }
}
