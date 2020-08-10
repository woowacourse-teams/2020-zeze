package dev.minguinho.zeze.domain.file.documentation;

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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import dev.minguinho.zeze.domain.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.domain.documentation.Documentation;
import dev.minguinho.zeze.domain.file.api.FileController;
import dev.minguinho.zeze.domain.file.api.dto.FileUrlResponses;
import dev.minguinho.zeze.domain.file.service.FileService;

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
    void upload() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("files", "image.png",
            MediaType.IMAGE_PNG_VALUE, "data".getBytes());
        FileUrlResponses fileUrlResponses = new FileUrlResponses(
            Collections.singletonList("https://markdown-ppt-test.s3.ap-northeast-2.amazonaws.com/"));
        given(fileService.upload(Arrays.asList(multipartFile, multipartFile))).willReturn(fileUrlResponses);

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
}
