package dev.minguinho.zeze.domain.slide.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import dev.minguinho.zeze.domain.auth.api.dto.request.GithubSignInDtoTest;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.domain.slide.api.dto.SlideRequestDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDtos;
import dev.minguinho.zeze.domain.slide.model.SlideRepository;
import dev.minguinho.zeze.domain.user.config.LoginUserIdMethodArgumentResolver;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SlideAcceptanceTest {
    public static final String BASE_URL = "/api/slides/";

    @LocalServerPort
    public int port;

    @Autowired
    private SlideRepository slideRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        slideRepository.deleteAll();
    }

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private LoginUserIdMethodArgumentResolver loginUserIdMethodArgumentResolver;

    public static RequestSpecification given() {
        return RestAssured.given()
            .log()
            .all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE);
    }

    @TestFactory
    @DisplayName("슬라이드 추가 조회 업데이트 삭제")
    Stream<DynamicTest> slide() {
        BDDMockito.given(jwtTokenProvider.validateToken(any())).willReturn(true);
        BDDMockito.given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        return Stream.of(
            dynamicTest("추가", () -> {
                String title = "제목";
                String content = "내용";
                String accessLevel = "PUBLIC";
                SlideRequestDto slideRequestDto = new SlideRequestDto(title, content, accessLevel);
                loginUser();

                createSlide(slideRequestDto);

                SlideResponseDtos slideResponseDtos = retrieveSlides();
                List<SlideResponseDto> values = slideResponseDtos.getValues();
                assertAll(
                    () -> assertThat(values.get(0).getTitle()).isEqualTo(title),
                    () -> assertThat(values.get(0).getContent()).isEqualTo(content),
                    () -> assertThat(values.get(0).getAccessLevel()).isEqualTo(accessLevel)
                );
            }),
            dynamicTest("조회", () -> {
                String title = "두번째 제목";
                String content = "두번째 내용";
                String accessLevel = "PRIVATE";
                SlideRequestDto slideRequestDto = new SlideRequestDto(title, content, accessLevel);

                createSlide(slideRequestDto);

                SlideResponseDtos slideResponseDtos = retrieveSlides();
                List<SlideResponseDto> values = slideResponseDtos.getValues();
                assertAll(
                    () -> assertThat(values.get(0).getTitle()).isEqualTo("제목"),
                    () -> assertThat(values.get(1).getTitle()).isEqualTo(title)
                );
            }),
            dynamicTest("특정 슬라이드 조회", () -> {
                SlideResponseDtos slideResponseDtos = retrieveSlides();
                Long id = slideResponseDtos.getValues().get(0).getId();

                SlideResponseDto slideResponseDto = retrieveSlide(id);

                assertAll(
                    () -> assertThat(slideResponseDto.getTitle()).isEqualTo("제목"),
                    () -> assertThat(slideResponseDto.getContent()).isEqualTo("내용"),
                    () -> assertThat(slideResponseDto.getAccessLevel()).isEqualTo("PUBLIC")
                );
            }),
            dynamicTest("업데이트", () -> {
                String title = "새 제목";
                String content = "내용";
                String accessLevel = "PUBLIC";
                SlideRequestDto slideRequestDto = new SlideRequestDto(title, content, accessLevel);
                SlideResponseDtos slideResponseDtos = retrieveSlides();
                List<SlideResponseDto> values = slideResponseDtos.getValues();
                Long id = values.get(0).getId();

                updateSlide(id, slideRequestDto);

                SlideResponseDtos result = retrieveSlides();
                List<SlideResponseDto> resultValues = result.getValues();
                assertAll(
                    () -> assertThat(resultValues.get(0).getTitle()).isEqualTo(title),
                    () -> assertThat(resultValues.get(0).getContent()).isEqualTo("내용"),
                    () -> assertThat(resultValues.get(0).getAccessLevel()).isEqualTo("PUBLIC")
                );
            }),
            dynamicTest("삭제", () -> {
                SlideResponseDtos slideResponseDtos = retrieveSlides();
                List<SlideResponseDto> values = slideResponseDtos.getValues();
                Long id = values.get(0).getId();

                deleteSlide(id);

                SlideResponseDtos result = retrieveSlides();
                List<SlideResponseDto> resultValues = result.getValues();
                assertAll(
                    () -> assertThat(resultValues.get(0).getTitle()).isEqualTo("두번째 제목"),
                    () -> assertThat(resultValues.get(0).getContent()).isEqualTo("두번째 내용"),
                    () -> assertThat(resultValues.get(0).getAccessLevel()).isEqualTo("PRIVATE")
                );
            })
        );
    }

    private void loginUser() {
        given()
            .body(GithubSignInDtoTest.getGithubSignInDtoFixture())
            .when()
            .post("/api/signin/github")
            .then()
            .log().all();
    }

    private void createSlide(SlideRequestDto slideRequestDto) {
        given()
            .auth()
            .oauth2("token")
            .body(slideRequestDto)
            .when()
            .post(BASE_URL)
            .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value());
    }

    private SlideResponseDto retrieveSlide(Long slideId) {
        return given()
            .auth()
            .oauth2("token")
            .when()
            .get(BASE_URL + slideId)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().as(SlideResponseDto.class);
    }

    private SlideResponseDtos retrieveSlides() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "0");
        params.put("size", "5");

        return given()
            .auth()
            .oauth2("token")
            .params(params)
            .when()
            .get(BASE_URL)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().as(SlideResponseDtos.class);
    }

    private void updateSlide(Long id, SlideRequestDto slideRequestDto) {
        given()
            .auth()
            .oauth2("token")
            .body(slideRequestDto)
            .when()
            .patch(BASE_URL + id)
            .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void deleteSlide(Long id) {
        given()
            .auth()
            .oauth2("token")
            .when()
            .delete(BASE_URL + id)
            .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
