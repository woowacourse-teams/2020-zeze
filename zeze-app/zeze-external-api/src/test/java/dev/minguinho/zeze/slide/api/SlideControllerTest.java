package dev.minguinho.zeze.slide.api;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

import dev.minguinho.zeze.auth.infra.AuthorizationTokenExtractor;
import dev.minguinho.zeze.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.slide.dto.SlideMetadataDto;
import dev.minguinho.zeze.slide.dto.SlideMetadataDtos;
import dev.minguinho.zeze.slide.dto.SlideRequestDto;
import dev.minguinho.zeze.slide.dto.SlidesRequestDto;
import dev.minguinho.zeze.slide.model.Slide;
import dev.minguinho.zeze.slide.service.SlideConvertor;
import dev.minguinho.zeze.slide.service.SlideService;
import dev.minguinho.zeze.user.config.LoginUserIdMethodArgumentResolver;

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
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        String accessLevel = "PUBLIC";
        SlideRequestDto slideRequestDto = new SlideRequestDto(title, subtitle, author, presentedAt, content,
            accessLevel);
        String body = objectMapper.writeValueAsString(slideRequestDto);
        given(authorizationTokenExtractor.extract(any(), any())).willReturn("");
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
        String firstSubtitle = "부제목1";
        String firstAuthor = "작성자1";
        String firstPresentedAt = "2020-07-21";
        String firstContent = "내용1";
        String secondTitle = "제목2";
        String secondSubtitle = "부제목2";
        String secondAuthor = "작성자2";
        String secondPresentedAt = "2020-07-22";
        String secondContent = "내용2";
        List<Slide> slides = Arrays.asList(
            new Slide(firstTitle, firstSubtitle, firstAuthor, firstPresentedAt, firstContent, Slide.AccessLevel.PUBLIC, 1L),
            new Slide(secondTitle, secondSubtitle, secondAuthor, secondPresentedAt, secondContent, Slide.AccessLevel.PRIVATE,
                1L));
        List<SlideMetadataDto> responses = slides.stream()
            .map(SlideConvertor::toSlideMetadataDto)
            .collect(Collectors.toList());
        given(slideService.retrieveAll(any(SlidesRequestDto.class), eq(null))).willReturn(
            new SlideMetadataDtos(responses, 0));
        given(authorizationTokenExtractor.extract(any(), any())).willReturn("");
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(null);

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
        String firstSubtitle = "부제목1";
        String firstAuthor = "작성자1";
        String firstPresentedAt = "2020-07-21";
        String firstContent = "내용1";
        String secondTitle = "제목2";
        String secondSubtitle = "부제목2";
        String secondAuthor = "작성자2";
        String secondPresentedAt = "2020-07-22";
        String secondContent = "내용2";
        List<Slide> slides = Arrays.asList(
            new Slide(firstTitle, firstSubtitle, firstAuthor, firstPresentedAt, firstContent, Slide.AccessLevel.PUBLIC, 1L),
            new Slide(secondTitle, secondSubtitle, secondAuthor, secondPresentedAt, secondContent, Slide.AccessLevel.PRIVATE,
                1L));
        List<SlideMetadataDto> responses = slides.stream()
            .map(SlideConvertor::toSlideMetadataDto)
            .collect(Collectors.toList());
        given(slideService.retrieveAll(any(SlidesRequestDto.class), eq(1L))).willReturn(
            new SlideMetadataDtos(responses, 0));
        given(authorizationTokenExtractor.extract(any(), any())).willReturn("");
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(firstTitle)))
            .andExpect(content().string(containsString(secondTitle)))
            .andDo(print());
    }

    @Test
    @DisplayName("특정 슬라이드 조회")
    void retrieveSlide() throws Exception {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        Slide slide = new Slide(title, subtitle, author, presentedAt, content, Slide.AccessLevel.PUBLIC);
        given(slideService.retrieve(1L, null)).willReturn(SlideConvertor.toResponseDto(slide));
        given(authorizationTokenExtractor.extract(any(), any())).willReturn("");
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(null);

        mvc.perform(get(BASE_URL + "1"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(content)))
            .andExpect(content().string(containsString("PUBLIC")))
            .andDo(print());
    }

    @Test
    @DisplayName("특정 User 슬라이드 조회")
    void retrieveUserSlide() throws Exception {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        Slide slide = new Slide(title, subtitle, author, presentedAt, content, Slide.AccessLevel.PUBLIC);
        given(slideService.retrieve(1L, 1L)).willReturn(SlideConvertor.toResponseDto(slide));
        given(authorizationTokenExtractor.extract(any(), any())).willReturn("");
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mvc.perform(get(BASE_URL + "1"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(content)))
            .andExpect(content().string(containsString("PUBLIC")))
            .andDo(print());
    }

    @Test
    @DisplayName("슬라이드 업데이트 요청")
    void updateSlide() throws Exception {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        String accessLevel = "PUBLIC";
        SlideRequestDto slideRequestDto = new SlideRequestDto(title, subtitle, author, presentedAt, content,
            accessLevel);
        String body = objectMapper.writeValueAsString(slideRequestDto);
        given(authorizationTokenExtractor.extract(any(), any())).willReturn("");
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

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
    void softDeleteSlide() throws Exception {
        given(authorizationTokenExtractor.extract(any(), any())).willReturn("");
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mvc.perform(delete(BASE_URL + "1"))
            .andExpect(status().isNoContent())
            .andDo(print());

        verify(slideService, times(1)).softDelete(eq(1L), anyLong());
    }

    @Test
    @DisplayName("슬라이드 복제 요청")
    void cloneSlide() throws Exception {
        given(authorizationTokenExtractor.extract(any(), any())).willReturn("");
        given(loginUserIdMethodArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginUserIdMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(1L);

        mvc.perform(post(BASE_URL + "1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isCreated())
            .andDo(print());

        verify(slideService, times(1)).clone(eq(1L), eq(1L));
    }
}
