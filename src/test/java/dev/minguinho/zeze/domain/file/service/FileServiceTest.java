package dev.minguinho.zeze.domain.file.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import dev.minguinho.zeze.domain.file.api.dto.FileUrlResponses;
import dev.minguinho.zeze.domain.file.model.S3Uploader;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {
    private FileService fileService;

    @Mock
    private S3Uploader s3Uploader;

    @BeforeEach
    void setUp() {
        fileService = new FileService(s3Uploader);
    }

    @Test
    @DisplayName("프레젠테이션에 있는 파일 List 업로드")
    void upload() {
        MultipartFile multipartFile = new MockMultipartFile("files", "test-image.png",
            MediaType.IMAGE_PNG_VALUE, "test-data".getBytes());
        String expected = "url";
        when(s3Uploader.upload(multipartFile)).thenReturn(expected);

        FileUrlResponses responses = fileService.upload(Arrays.asList(multipartFile, multipartFile));

        assertAll(
            () -> assertThat(responses.getUrls().get(0)).isEqualTo(expected),
            () -> assertThat(responses.getUrls().get(1)).isEqualTo(expected)
        );
    }
}
