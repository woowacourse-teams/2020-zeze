package dev.minguinho.zeze.domain.file.api;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import dev.minguinho.zeze.domain.file.model.S3Uploader;
import dev.minguinho.zeze.domain.file.exception.FileNotConvertedException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PresentationControllerTest {
    private MockMvc mvc;

    @MockBean
    private S3Uploader s3Uploader;

    @BeforeEach
    void setUp(WebApplicationContext applicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(applicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    @DisplayName("파일 업로드 요청")
    void uploadFile() throws Exception {
        String filePath = "src/test/resources";
        String fileName = "test-image.png";
        File file = new File(String.format("%s/%s", filePath, fileName));
        MockMultipartFile mockMultipartFile = new MockMultipartFile("files", fileName,
            MediaType.IMAGE_PNG_VALUE, new FileInputStream(file));

        when(s3Uploader.upload(mockMultipartFile)).thenReturn("https://markdown-ppt-test.s3.ap-northeast-2.amazonaws.com/");

        mvc.perform(multipart("/api/files")
            .file(mockMultipartFile)
            .file(mockMultipartFile)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        )
            .andExpect(status().isOk())
            .andExpect(
                content().string(containsString(String.format("https://%s.s3.ap-northeast-2.amazonaws.com/", "markdown-ppt-test"))))
            .andDo(print());
    }

    @Test
    @DisplayName("파일 업로드에 실패하는 경우")
    void uploadFileFail() throws Exception {
        String fileName = "test-image.png";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("files", (byte[])null);

        when(s3Uploader.upload(mockMultipartFile)).thenThrow(new FileNotConvertedException(fileName));

        mvc.perform(multipart("/api/files")
            .file(mockMultipartFile)
            .file(mockMultipartFile)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        )
            .andExpect(status().isBadRequest())
            .andDo(print());
    }
}
