package dev.minguinho.zeze.domain.slide.documentation;

import static io.restassured.config.EncoderConfig.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;

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

    private AuthenticationDto authenticationDto;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        authenticationDto = AuthenticationDto.builder().accessToken("token").build();
    }

    @Test
    void createSlide() {
        SlideRequestDto slideRequestDto = new SlideRequestDto("제목", "내용", "PUBLIC");
        BDDMockito.given(slideService.create(any(), anyLong())).willReturn(1L);
        BDDMockito.given(jwtTokenProvider.validateToken(any())).willReturn(true);
        BDDMockito.given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        BDDMockito.given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        given()
            .log().all()
            .header("Authorization", "bearer " + authenticationDto.getAccessToken())
            .config(RestAssuredMockMvcConfig.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(slideRequestDto)
            .when()
            .post(BASE_URL)
            .then()
            .log().all()
            .apply(document("slides/create",
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
            ).extract();
    }

    @Test
    void retrieveSlides() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "0");
        params.put("size", "5");
        List<SlideResponseDto> slides = Collections.singletonList(
            new SlideResponseDto(1L, "제목", "내용", "PUBLIC", ZonedDateTime.now(), ZonedDateTime.now())
        );
        SlideResponseDtos slideResponseDtos = new SlideResponseDtos(slides);
        BDDMockito.given(slideService.retrieveSlides(any())).willReturn(slideResponseDtos);

        given()
            .log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .params(params)
            .when()
            .get(BASE_URL)
            .then()
            .log().all()
            .apply(document("slides/retrieveAllPublic",
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
            ).extract();
    }

    @Test
    void retrieveMySlides() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "0");
        params.put("size", "5");
        List<SlideResponseDto> slides = Collections.singletonList(
            new SlideResponseDto(1L, "제목", "내용", "PUBLIC", ZonedDateTime.now(), ZonedDateTime.now())
        );
        SlideResponseDtos slideResponseDtos = new SlideResponseDtos(slides);
        BDDMockito.given(slideService.retrieveSlides(any(), anyLong())).willReturn(slideResponseDtos);
        BDDMockito.given(jwtTokenProvider.validateToken(any())).willReturn(true);
        BDDMockito.given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        BDDMockito.given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        given()
            .log().all()
            .header("Authorization", "bearer " + authenticationDto.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .params(params)
            .when()
            .get(BASE_URL + "me")
            .then()
            .log().all()
            .apply(document("slides/retrieveAll",
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
            ).extract();
    }

    @Test
    void retrieveSlide() {
        SlideResponseDto slideResponseDto = new SlideResponseDto(1L, "제목", "내용", "PUBLIC", ZonedDateTime.now(),
            ZonedDateTime.now());
        BDDMockito.given(slideService.retrieveSlide(anyLong())).willReturn(slideResponseDto);

        given()
            .log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(BASE_URL + 1)
            .then()
            .log().all()
            .apply(document("slides/retrievePublic",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("슬라이드 id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("슬라이드 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("슬라이드 내용"),
                    fieldWithPath("accessLevel").type(JsonFieldType.STRING).description("슬라이드 접근 권한"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("슬라이드 생성 날짜"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("슬라이드 수정 날짜")))
            ).extract();
    }

    @Test
    void retrieveMySlide() {
        SlideResponseDto slideResponseDto = new SlideResponseDto(1L, "제목", "내용", "PRIVATE", ZonedDateTime.now(),
            ZonedDateTime.now());
        BDDMockito.given(slideService.retrieveSlide(anyLong())).willReturn(slideResponseDto);

        given()
            .log().all()
            .header("Authorization", "bearer " + authenticationDto.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(BASE_URL + 1)
            .then()
            .log().all()
            .apply(document("slides/retrieve",
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
            ).extract();
    }
}
