package dev.minguinho.zeze.domain.file.api;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import dev.minguinho.zeze.domain.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.domain.file.api.dto.FileUrlResponses;
import dev.minguinho.zeze.domain.file.exception.FileNotConvertedException;
import dev.minguinho.zeze.domain.file.service.FileService;

@WebMvcTest(controllers = {FileController.class})
class FileControllerTest {
    private MockMvc mvc;

    @MockBean
    private FileService fileService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthorizationTokenExtractor authorizationTokenExtractor;

    @BeforeEach
    void setUp(WebApplicationContext applicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(applicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    @DisplayName("파일 업로드 요청")
    void uploadFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("files", "test-image.png",
            MediaType.IMAGE_PNG_VALUE, "test-data".getBytes());

        given(fileService.upload(Arrays.asList(multipartFile, multipartFile))).willReturn(
            new FileUrlResponses(
                Collections.singletonList("https://markdown-ppt-test.s3.ap-northeast-2.amazonaws.com/")));

        mvc.perform(multipart("/api/files")
            .file(multipartFile)
            .file(multipartFile)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        )
            .andExpect(status().isOk())
            .andExpect(
                content().string(
                    containsString("https://markdown-ppt-test.s3.ap-northeast-2.amazonaws.com/")))
            .andDo(print());
    }

    @Test
    @DisplayName("파일 업로드에 실패하는 경우")
    void uploadFileFail() throws Exception {
        String fileName = "test-image.png";
        MockMultipartFile multipartFile = new MockMultipartFile("files", fileName,
            MediaType.IMAGE_PNG_VALUE, "test-data".getBytes());

        when(fileService.upload(Arrays.asList(multipartFile, multipartFile))).thenThrow(
            new FileNotConvertedException(fileName));

        mvc.perform(multipart("/api/files")
            .file(multipartFile)
            .file(multipartFile)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        )
            .andExpect(status().isBadRequest())
            .andExpect(
                content().string(containsString(String.format("%s의 파일 변환에 실패했습니다.", fileName))))
            .andDo(print());
    }
}
