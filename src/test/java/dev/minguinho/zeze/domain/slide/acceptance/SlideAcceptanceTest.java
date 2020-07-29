package dev.minguinho.zeze.domain.slide.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.minguinho.zeze.domain.slide.api.dto.SlideRequestDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDtos;
import dev.minguinho.zeze.domain.slide.model.SlideRepository;

@SpringBootTest
public class SlideAcceptanceTest {
    public static final String BASE_URL = "/api/slides/";

    private MockMvc mvc;

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @AfterEach
    void tearDown() {
        slideRepository.deleteAll();
    }

    @TestFactory
    @DisplayName("슬라이드 추가 조회 업데이트 삭제")
    Stream<DynamicTest> slide() {
        return Stream.of(
            dynamicTest("추가", () -> {
                String title = "제목";
                String content = "내용";
                String accessLevel = "PUBLIC";
                String body = contentOfRequest(title, content, accessLevel);

                createSlide(body);

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
                String body = contentOfRequest(title, content, accessLevel);

                createSlide(body);

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
                String body = contentOfRequest(title, null, null);
                SlideResponseDtos slideResponseDtos = retrieveSlides();
                List<SlideResponseDto> values = slideResponseDtos.getValues();
                Long id = values.get(0).getId();

                updateSlide(id, body);

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

    private String contentOfRequest(String title, String content, String contentType) throws JsonProcessingException {
        SlideRequestDto slideRequestDto = new SlideRequestDto(title, content, contentType);
        return objectMapper.writeValueAsString(slideRequestDto);
    }

    private void createSlide(String body) throws Exception {
        mvc.perform(post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(body)
        )
            .andExpect(status().isCreated())
            .andDo(print());
    }

    private SlideResponseDto retrieveSlide(Long slideId) throws Exception {
        String content = mvc.perform(get(BASE_URL + slideId))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(content, SlideResponseDto.class);
    }

    private SlideResponseDtos retrieveSlides() throws Exception {
        String content = mvc.perform(get(BASE_URL)
            .param("id", "0")
            .param("size", "5")
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(content, SlideResponseDtos.class);
    }

    private void updateSlide(Long id, String body) throws Exception {
        mvc.perform(patch(BASE_URL + id)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(body)
        )
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    private void deleteSlide(Long id) throws Exception {
        mvc.perform(delete(BASE_URL + id))
            .andExpect(status().isNoContent())
            .andDo(print());
    }
}
