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
import dev.minguinho.zeze.domain.slide.api.dto.SlideRequest;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponse;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponses;
import dev.minguinho.zeze.domain.slide.model.SlideRepository;

@SpringBootTest
public class SlideAcceptanceTest {
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
                String contentType = "타입";
                String body = contentOfRequest(title, content, contentType);

                createSlide(body);

                SlideResponses slideResponses = retrieveSlides();
                List<SlideResponse> values = slideResponses.getValues();
                assertAll(
                    () -> assertThat(values.get(0).getTitle()).isEqualTo(title),
                    () -> assertThat(values.get(0).getContent()).isEqualTo(content),
                    () -> assertThat(values.get(0).getContentType()).isEqualTo(contentType)
                );
            }),
            dynamicTest("조회", () -> {
                String title = "두번째 제목";
                String content = "두번째 내용";
                String contentType = "두번째 타입";
                String body = contentOfRequest(title, content, contentType);

                createSlide(body);

                SlideResponses slideResponses = retrieveSlides();
                List<SlideResponse> values = slideResponses.getValues();
                assertAll(
                    () -> assertThat(values.get(0).getTitle()).isEqualTo("제목"),
                    () -> assertThat(values.get(1).getTitle()).isEqualTo(title)
                );
            }),
            dynamicTest("업데이트", () -> {
                String title = "새 제목";
                String body = contentOfRequest(title, null, null);
                SlideResponses slideResponses = retrieveSlides();
                List<SlideResponse> values = slideResponses.getValues();
                Long id = values.get(0).getId();

                updateSlide(id, body);

                SlideResponses result = retrieveSlides();
                List<SlideResponse> resultValues = result.getValues();
                assertAll(
                    () -> assertThat(resultValues.get(0).getTitle()).isEqualTo(title),
                    () -> assertThat(resultValues.get(0).getContent()).isEqualTo("내용"),
                    () -> assertThat(resultValues.get(0).getContentType()).isEqualTo("타입")
                );
            }),
            dynamicTest("삭제", () -> {
                SlideResponses slideResponses = retrieveSlides();
                List<SlideResponse> values = slideResponses.getValues();
                Long id = values.get(0).getId();

                deleteSlide(id);

                SlideResponses result = retrieveSlides();
                List<SlideResponse> resultValues = result.getValues();
                assertAll(
                    () -> assertThat(resultValues.get(0).getTitle()).isEqualTo("두번째 제목"),
                    () -> assertThat(resultValues.get(0).getContent()).isEqualTo("두번째 내용"),
                    () -> assertThat(resultValues.get(0).getContentType()).isEqualTo("두번째 타입")
                );
            })
        );
    }

    private String contentOfRequest(String title, String content, String contentType) throws JsonProcessingException {
        SlideRequest slideRequest = new SlideRequest(title, content, contentType);
        return objectMapper.writeValueAsString(slideRequest);
    }

    private void createSlide(String body) throws Exception {
        mvc.perform(post("/api/slides")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(body)
        )
            .andExpect(status().isCreated())
            .andDo(print());
    }

    private SlideResponses retrieveSlides() throws Exception {
        String content = mvc.perform(get("/api/slides"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(content, SlideResponses.class);
    }

    private void updateSlide(Long id, String body) throws Exception {
        mvc.perform(patch("/api/slides/" + id)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(body)
        )
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    private void deleteSlide(Long id) throws Exception {
        mvc.perform(delete("/api/slides/" + id))
            .andExpect(status().isNoContent())
            .andDo(print());
    }
}
