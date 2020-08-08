package dev.minguinho.zeze.domain.slide.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import dev.minguinho.zeze.domain.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.domain.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.domain.documentation.Documentation;
import dev.minguinho.zeze.domain.slide.api.SlideController;
import dev.minguinho.zeze.domain.slide.api.dto.SlideRequestDto;
import dev.minguinho.zeze.domain.slide.service.SlideService;

@WebMvcTest(controllers = {SlideController.class})
public class SlideDocumentation extends Documentation {
    private static final String BASE_URL = "/api/slides";

    @MockBean
    private SlideService slideService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthorizationTokenExtractor authorizationTokenExtractor;

    private AuthenticationDto authenticationDto;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        authenticationDto = AuthenticationDto.builder().accessToken("token").build();
    }

    @Test
    void createSlide() {
        SlideRequestDto slideRequestDto = new SlideRequestDto("제목", "내용", "PUBLIC");

        given()
            .log().all()
            .header("Authorization", "bearer" + authenticationDto.getAccessToken())
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
                    fieldWithPath("accessLevel").type(JsonFieldType.STRING).description("접근 레벨")))
            ).extract();
    }
}
