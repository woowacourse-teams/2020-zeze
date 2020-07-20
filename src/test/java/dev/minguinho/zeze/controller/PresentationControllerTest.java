package dev.minguinho.zeze.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class PresentationControllerTest {
    private MockMvc mvc;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

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

        mvc.perform(multipart("/api/files")
            .file(mockMultipartFile)
            .file(mockMultipartFile)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        )
            .andExpect(status().isOk())
            .andExpect(
                content().string(containsString(String.format("https://%s.s3.ap-northeast-2.amazonaws.com/", bucket))))
            .andDo(print());
    }
}
