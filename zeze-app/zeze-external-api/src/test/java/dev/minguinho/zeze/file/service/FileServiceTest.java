package dev.minguinho.zeze.file.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
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

import dev.minguinho.zeze.file.dto.FileUploadRequestDto;
import dev.minguinho.zeze.file.dto.FileUrlResponsesDto;
import dev.minguinho.zeze.file.model.S3Uploader;

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
    @DisplayName("슬라이드에 있는 MultipartFile List 업로드")
    void uploadMultipartFile() {
        MultipartFile multipartFile = new MockMultipartFile("files", "test-image.png",
            MediaType.IMAGE_PNG_VALUE, "test-data".getBytes());
        String expected = "url";
        when(s3Uploader.uploadMultiPartFile(multipartFile)).thenReturn(expected);

        FileUrlResponsesDto responses = fileService.upload(Arrays.asList(multipartFile, multipartFile));

        assertAll(
            () -> assertThat(responses.getUrls().get(0)).isEqualTo(expected),
            () -> assertThat(responses.getUrls().get(1)).isEqualTo(expected)
        );
    }

    @Test
    @DisplayName("슬라이드에 있는 외부 자원 업로드")
    void uploadExternalFile() throws IOException {
        String fileUrl = "fileUrl";
        String fileName = "fileName";
        FileUploadRequestDto fileUploadRequestDto = new FileUploadRequestDto(fileUrl, fileName);
        when(s3Uploader.uploadExternalFile(fileUrl, fileName)).thenReturn(fileUrl);

        FileUrlResponsesDto response = fileService.upload(fileUploadRequestDto);

        assertThat(response.getUrls().get(0)).isEqualTo(fileUrl);
    }
}
