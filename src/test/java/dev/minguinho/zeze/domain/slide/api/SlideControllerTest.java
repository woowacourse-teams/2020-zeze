package dev.minguinho.zeze.domain.slide.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
}
