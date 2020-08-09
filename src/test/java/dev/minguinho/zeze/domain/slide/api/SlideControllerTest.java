package dev.minguinho.zeze.domain.slide.api;

import static dev.minguinho.zeze.domain.slide.model.Slide.*;
import static org.hamcrest.core.StringContains.*;
import static org.mockito.BDDMockito.*;
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

import dev.minguinho.zeze.domain.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.domain.slide.api.dto.SlideRequestDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDtos;
import dev.minguinho.zeze.domain.slide.api.dto.SlidesRequestDto;
import dev.minguinho.zeze.domain.slide.model.Slide;
import dev.minguinho.zeze.domain.slide.service.SlideService;
import dev.minguinho.zeze.domain.user.config.LoginUserIdMethodArgumentResolver;

@WebMvcTest(controllers = {SlideController.class})
class SlideControllerTest {
    public static final String BASE_URL = "/api/slides/";

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SlideService slideService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthorizationTokenExtractor authorizationTokenExtractor;

    @MockBean
    private LoginUserIdMethodArgumentResolver loginUserIdMethodArgumentResolver;

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
        String accessLevel = "PUBLIC";
        SlideRequestDto slideRequestDto = new SlideRequestDto(title, content, accessLevel);
        String body = objectMapper.writeValueAsString(slideRequestDto);

        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mvc.perform(post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(body)
        )
            .andExpect(status().isCreated())
            .andDo(print());

        verify(slideService, times(1)).create(any(SlideRequestDto.class), eq(1L));
    }

    @Test
    @DisplayName("슬라이드 list 요청")
    void retrieveSlides() throws Exception {
        String firstTitle = "제목1";
        String firstContent = "내용1";
        String secondTitle = "제목2";
        String secondContent = "내용2";
        List<Slide> slides = Arrays.asList(new Slide(firstTitle, firstContent, AccessLevel.PUBLIC),
            new Slide(secondTitle, secondContent, AccessLevel.PUBLIC));
        given(slideService.retrieveSlides(any(SlidesRequestDto.class))).willReturn(
            SlideResponseDtos.from(slides));

        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(firstTitle)))
            .andExpect(content().string(containsString(secondTitle)))
            .andDo(print());
    }

    @Test
    @DisplayName("User 슬라이드 list 요청")
    void retrieveMySlides() throws Exception {
        String firstTitle = "제목1";
        String firstContent = "내용1";
        String secondTitle = "제목2";
        String secondContent = "내용2";
        List<Slide> slides = Arrays.asList(new Slide(firstTitle, firstContent, AccessLevel.PUBLIC),
            new Slide(secondTitle, secondContent, AccessLevel.PRIVATE));
        given(slideService.retrieveSlides(any(SlidesRequestDto.class), anyLong())).willReturn(
            SlideResponseDtos.from(slides));

        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mvc.perform(get(BASE_URL + "me"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(firstTitle)))
            .andExpect(content().string(containsString(secondTitle)))
            .andDo(print());
    }

    @Test
    @DisplayName("특정 슬라이드 조회")
    void retrieveSlide() throws Exception {
        String title = "제목";
        String content = "내용";
        Slide slide = new Slide(title, content, AccessLevel.PUBLIC);
        given(slideService.retrieveSlide(1L)).willReturn(SlideResponseDto.from(slide));

        mvc.perform(get(BASE_URL + "1"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(title)))
            .andExpect(content().string(containsString(content)))
            .andExpect(content().string(containsString("PUBLIC")))
            .andDo(print());
    }

    @Test
    @DisplayName("특정 User 슬라이드 조회")
    void retrieveUserSlide() throws Exception {
        String title = "제목";
        String content = "내용";
        Slide slide = new Slide(title, content, AccessLevel.PUBLIC);
        given(slideService.retrieveSlide(1L, 1L)).willReturn(SlideResponseDto.from(slide));
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mvc.perform(get(BASE_URL + "me/1"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(title)))
            .andExpect(content().string(containsString(content)))
            .andExpect(content().string(containsString("PUBLIC")))
            .andDo(print());
    }

    @Test
    @DisplayName("슬라이드 업데이트 요청")
    void updateSlide() throws Exception {
        String title = "제목";
        String content = "내용";
        String accessLevel = "PUBLIC";
        SlideRequestDto slideRequestDto = new SlideRequestDto(title, content, accessLevel);

        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        String body = objectMapper.writeValueAsString(slideRequestDto);

        mvc.perform(patch(BASE_URL + "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(body)
        )
            .andExpect(status().isNoContent())
            .andDo(print());

        verify(slideService, times(1)).update(eq(1L), any(SlideRequestDto.class), eq(1L));
    }

    @Test
    @DisplayName("슬라이드 삭제 요청")
    void deleteSlide() throws Exception {
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mvc.perform(delete(BASE_URL + "1"))
            .andExpect(status().isNoContent())
            .andDo(print());

        verify(slideService, times(1)).delete(eq(1L), anyLong());
    }
}
