package dev.minguinho.zeze.domain.slide.documentation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.minguinho.zeze.domain.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.domain.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.domain.documentation.Documentation;
import dev.minguinho.zeze.domain.slide.api.SlideController;
import dev.minguinho.zeze.domain.slide.api.dto.SlideRequestDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDtos;
import dev.minguinho.zeze.domain.slide.service.SlideService;
import dev.minguinho.zeze.domain.user.config.LoginUserIdMethodArgumentResolver;

@WebMvcTest(controllers = {SlideController.class})
public class SlideDocumentation extends Documentation {
    private static final String BASE_URL = "/api/slides/";

    @MockBean
    private SlideService slideService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthorizationTokenExtractor authorizationTokenExtractor;

    @MockBean
    private LoginUserIdMethodArgumentResolver loginUserIdMethodArgumentResolver;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthenticationDto authenticationDto;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        authenticationDto = AuthenticationDto.builder().accessToken("token").build();
    }

    @Test
    void createSlide() throws Exception {
        SlideRequestDto slideRequestDto = new SlideRequestDto("제목", "내용", "PUBLIC");
        given(slideService.create(any(), anyLong())).willReturn(1L);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);
        String content = objectMapper.writeValueAsString(slideRequestDto);

        mockMvc.perform(post(BASE_URL)
            .header("Authorization", "bearer " + authenticationDto.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON)
            .content(content)
        )
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("slides/create",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("accessLevel").type(JsonFieldType.STRING).description("접근 레벨")
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 슬라이드 URI")
                ))
            );

    }

    @Test
    void retrieveSlides() throws Exception {
        List<SlideResponseDto> slides = Collections.singletonList(
            new SlideResponseDto(1L, "제목", "내용", "PUBLIC", ZonedDateTime.now(), ZonedDateTime.now())
        );
        SlideResponseDtos slideResponseDtos = new SlideResponseDtos(slides);
        given(slideService.retrieveSlides(any())).willReturn(slideResponseDtos);

        mockMvc.perform(get(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .param("id", "0")
            .param("size", "5")
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("slides/retrieveAllPublic",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("slides").type(JsonFieldType.ARRAY).description("Public 슬라이드 목록"),
                    fieldWithPath("slides[0].id").type(JsonFieldType.NUMBER).description("슬라이드 id"),
                    fieldWithPath("slides[0].title").type(JsonFieldType.STRING).description("슬라이드 제목"),
                    fieldWithPath("slides[0].content").type(JsonFieldType.STRING).description("슬라이드 내용"),
                    fieldWithPath("slides[0].accessLevel").type(JsonFieldType.STRING).description("슬라이드 접근 권한"),
                    fieldWithPath("slides[0].createdAt").type(JsonFieldType.STRING).description("슬라이드 생성 날짜"),
                    fieldWithPath("slides[0].updatedAt").type(JsonFieldType.STRING).description("슬라이드 수정 날짜")))
            );
    }

    @Test
    void retrieveMySlides() throws Exception {
        List<SlideResponseDto> slides = Collections.singletonList(
            new SlideResponseDto(1L, "제목", "내용", "PUBLIC", ZonedDateTime.now(), ZonedDateTime.now())
        );
        SlideResponseDtos slideResponseDtos = new SlideResponseDtos(slides);
        given(slideService.retrieveSlides(any(), anyLong())).willReturn(slideResponseDtos);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mockMvc.perform(get(BASE_URL + "me")
            .header("Authorization", "bearer " + authenticationDto.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .param("id", "0")
            .param("size", "5")
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("slides/retrieveAll",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")
                ),
                responseFields(
                    fieldWithPath("slides").type(JsonFieldType.ARRAY).description("User 슬라이드 목록"),
                    fieldWithPath("slides[0].id").type(JsonFieldType.NUMBER).description("슬라이드 id"),
                    fieldWithPath("slides[0].title").type(JsonFieldType.STRING).description("슬라이드 제목"),
                    fieldWithPath("slides[0].content").type(JsonFieldType.STRING).description("슬라이드 내용"),
                    fieldWithPath("slides[0].accessLevel").type(JsonFieldType.STRING).description("슬라이드 접근 권한"),
                    fieldWithPath("slides[0].createdAt").type(JsonFieldType.STRING).description("슬라이드 생성 날짜"),
                    fieldWithPath("slides[0].updatedAt").type(JsonFieldType.STRING).description("슬라이드 수정 날짜")))
            );

    }

    @Test
    void retrieveSlide() throws Exception {
        SlideResponseDto slideResponseDto = new SlideResponseDto(1L, "제목", "내용", "PUBLIC", ZonedDateTime.now(),
            ZonedDateTime.now());
        given(slideService.retrieveSlide(anyLong())).willReturn(slideResponseDto);

        mockMvc.perform(get(BASE_URL + 1)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("slides/retrievePublic",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("슬라이드 id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("슬라이드 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("슬라이드 내용"),
                    fieldWithPath("accessLevel").type(JsonFieldType.STRING).description("슬라이드 접근 권한"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("슬라이드 생성 날짜"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("슬라이드 수정 날짜")))
            );
    }

    @Test
    void retrieveMySlide() throws Exception {
        SlideResponseDto slideResponseDto = new SlideResponseDto(1L, "제목", "내용", "PRIVATE", ZonedDateTime.now(),
            ZonedDateTime.now());
        given(slideService.retrieveSlide(anyLong())).willReturn(slideResponseDto);

        mockMvc.perform(get(BASE_URL + 1)
            .header("Authorization", "bearer " + authenticationDto.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("slides/retrieve",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("슬라이드 id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("슬라이드 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("슬라이드 내용"),
                    fieldWithPath("accessLevel").type(JsonFieldType.STRING).description("슬라이드 접근 권한"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("슬라이드 생성 날짜"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("슬라이드 수정 날짜")))
            );
    }

    @Test
    void updateSlide() throws Exception {
        SlideRequestDto updateRequestDto = new SlideRequestDto("새 제목", "새 내용", "PRIVATE");
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);
        String content = objectMapper.writeValueAsString(updateRequestDto);

        mockMvc.perform(patch(BASE_URL + 1)
            .header("Authorization", "bearer " + authenticationDto.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(content)
        )
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("slides/update",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 내용"),
                    fieldWithPath("accessLevel").type(JsonFieldType.STRING).description("수정할 접근 레벨")))
            );
    }

    @Test
    void deleteSlide() throws Exception {
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mockMvc.perform(delete(BASE_URL + 1)
            .header("Authorization", "bearer " + authenticationDto.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("slides/delete",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")))
            );
    }
}
