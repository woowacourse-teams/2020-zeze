package dev.minguinho.zeze.domain.slide.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.minguinho.zeze.domain.slide.api.dto.SlideRequest;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponses;
import dev.minguinho.zeze.domain.slide.model.Slide;
import dev.minguinho.zeze.domain.slide.model.SlideRepository;

@ExtendWith(MockitoExtension.class)
class SlideServiceTest {
    private SlideService slideService;

    @Mock
    private SlideRepository slideRepository;

    @BeforeEach
    void setUp() {
        this.slideService = new SlideService(slideRepository);
    }

    @Test
    @DisplayName("슬라이드 생성")
    void createSlide() {
        String title = "제목";
        String content = "내용";
        String contentType = "타입";
        SlideRequest slideRequest = new SlideRequest(title, content, contentType);

        slideService.createSlide(slideRequest);

        verify(slideRepository, times(1)).save(any(Slide.class));
    }

    @Test
    @DisplayName("슬라이드 전체 조회")
    void retrieveSlides() {
        String firstTitle = "제목1";
        String firstContent = "내용1";
        String firstContentType = "타입1";
        String secondTitle = "제목2";
        String secondContent = "내용2";
        String secondContentType = "타입2";
        List<Slide> slides = Arrays.asList(new Slide(firstTitle, firstContent, firstContentType),
            new Slide(secondTitle, secondContent, secondContentType));
        when(slideRepository.findAll()).thenReturn(slides);

        SlideResponses slideResponses = slideService.retrieveSlides();

        assertAll(
            () -> assertThat(slideResponses.getValues().get(0).getTitle()).isEqualTo(firstTitle),
            () -> assertThat(slideResponses.getValues().get(1).getTitle()).isEqualTo(secondTitle)
        );
    }
}
