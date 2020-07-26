package dev.minguinho.zeze.domain.slide.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.minguinho.zeze.domain.slide.api.dto.SlideRequest;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponses;
import dev.minguinho.zeze.domain.slide.exception.SlideNotFoundException;
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

    @Test
    @DisplayName("슬라이드 업데이트")
    void updateSlide() {
        String title = "제목";
        String content = "내용";
        String contentType = "타입";
        Slide slide = new Slide(title, content, contentType);
        when(slideRepository.findById(1L)).thenReturn(Optional.of(slide));
        String newTitle = "새 제목";
        when(slideRepository.save(any(Slide.class))).thenReturn(new Slide(newTitle, content, contentType));

        SlideRequest slideRequest = new SlideRequest(newTitle, null, null);
        slideService.updateSlide(1L, slideRequest);

        verify(slideRepository, times(1)).save(any(Slide.class));
        assertAll(
            () -> assertThat(slide.getTitle()).isEqualTo(newTitle),
            () -> assertThat(slide.getContent()).isEqualTo(content),
            () -> assertThat(slide.getContentType()).isEqualTo(contentType)
        );
    }

    @Test
    @DisplayName("슬라이드가 존재하지 않을 경우")
    void updateWithInvalidSlide() {
        when(slideRepository.findById(1L)).thenThrow(new SlideNotFoundException(1L));

        assertThatThrownBy(() -> slideService.updateSlide(1L, null))
            .isInstanceOf(SlideNotFoundException.class)
            .hasMessage("Slide Id : 1 해당 슬라이드는 존재하지 않습니다.");
    }

    @Test
    @DisplayName("슬라이드 삭제")
    void deleteSlide() {
        slideService.deleteSlide(1L);

        verify(slideRepository, times(1)).deleteById(1L);
    }
}
