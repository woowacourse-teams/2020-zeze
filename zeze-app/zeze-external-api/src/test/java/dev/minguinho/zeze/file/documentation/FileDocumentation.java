package dev.minguinho.zeze.file.documentation;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import dev.minguinho.zeze.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.documentation.Documentation;
import dev.minguinho.zeze.file.api.FileController;
import dev.minguinho.zeze.file.dto.FileUploadRequestDto;
import dev.minguinho.zeze.file.dto.FileUrlResponsesDto;
import dev.minguinho.zeze.file.service.FileService;

@WebMvcTest(controllers = {FileController.class})
public class FileDocumentation extends Documentation {
    @MockBean
    private FileService fileService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthorizationTokenExtractor authorizationTokenExtractor;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
    }

    @Test
    void uploadMultipartFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("files", "image.png",
            MediaType.IMAGE_PNG_VALUE, "data".getBytes());
        FileUrlResponsesDto fileUrlResponsesDto = new FileUrlResponsesDto(
            Collections.singletonList("https://markdown-ppt-test.s3.ap-northeast-2.amazonaws.com/"));
        given(fileService.upload(Arrays.asList(multipartFile, multipartFile))).willReturn(fileUrlResponsesDto);

        mockMvc.perform(multipart("/api/files")
            .file(multipartFile)
            .file(multipartFile)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("files/upload",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParts(
                    partWithName("files").description("업로드할 파일들")
                ),
                responseFields(
                    fieldWithPath("urls").type(JsonFieldType.ARRAY).description("파일 URL 목록"),
                    fieldWithPath("urls[0]").type(JsonFieldType.ARRAY).description("파일 URL")
                ))
            );
    }

    @Test
    void uploadExternalFile() throws Exception {
        given(fileService.upload(BDDMockito.any(FileUploadRequestDto.class))).willReturn(
            new FileUrlResponsesDto(
                Collections.singletonList("https://markdown-ppt-test.s3.ap-northeast-2.amazonaws.com/")));

        mockMvc.perform(post("/api/files/external")
            .content("{\"fileUrl\":\"fileUrl\",\"fileName\":\"fileName\"}")
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk())
            .andExpect(
                content().string(
                    containsString("https://markdown-ppt-test.s3.ap-northeast-2.amazonaws.com/")))
            .andDo(print())
            .andDo(document("files/upload-external",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("fileUrl").type(JsonFieldType.STRING).description("외부 파일 위치"),
                    fieldWithPath("fileName").type(JsonFieldType.STRING).description("외부 파일 이름")
                ),
                responseFields(
                    fieldWithPath("urls").type(JsonFieldType.ARRAY).description("파일 URL 목록"),
                    fieldWithPath("urls[0]").type(JsonFieldType.ARRAY).description("파일 URL")
                ))
            );
    }
}
