package dev.minguinho.zeze.domain.slide.api;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.minguinho.zeze.domain.slide.api.dto.SlideRequest;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponses;
import dev.minguinho.zeze.domain.slide.model.Slide;
import dev.minguinho.zeze.domain.slide.service.SlideService;

@WebMvcTest(controllers = {SlideController.class})
class SlideControllerTest {
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SlideService slideService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    @DisplayName("슬라이드 생성 요청")
    void createSlide() throws Exception {
        String title = "제목";
        String content = "내용";
        String contentType = "타입";
        SlideRequest slideRequest = new SlideRequest(title, content, contentType);
        String body = objectMapper.writeValueAsString(slideRequest);

        mvc.perform(post("/api/slides")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(body)
        )
            .andExpect(status().isCreated())
            .andDo(print());

        verify(slideService, times(1)).createSlide(any(SlideRequest.class));
    }

    @Test
    @DisplayName("슬라이드 전체 조회 요청")
    void retrieveSlides() throws Exception {
        String firstTitle = "제목1";
        String firstContent = "내용1";
        String firstContentType = "타입1";
        String secondTitle = "제목2";
        String secondContent = "내용2";
        String secondContentType = "타입2";
        List<Slide> slides = Arrays.asList(new Slide(firstTitle, firstContent, firstContentType),
            new Slide(secondTitle, secondContent, secondContentType));
        when(slideService.retrieveSlides()).thenReturn(SlideResponses.from(slides));

        mvc.perform(get("/api/slides"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(firstTitle)))
            .andExpect(content().string(containsString(secondTitle)))
            .andDo(print());
    }
}
