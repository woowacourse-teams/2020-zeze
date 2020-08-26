package dev.minguinho.zeze.domain.slide.service;

import static dev.minguinho.zeze.domain.slide.model.Slide.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.minguinho.zeze.domain.slide.api.dto.SlideMetadataDtos;
import dev.minguinho.zeze.domain.slide.api.dto.SlideRequestDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlidesRequestDto;
import dev.minguinho.zeze.domain.slide.exception.SlideNotAuthorizedException;
import dev.minguinho.zeze.domain.slide.exception.SlideNotFoundException;
import dev.minguinho.zeze.domain.slide.model.Slide;
import dev.minguinho.zeze.domain.slide.model.SlideRepository;

@ExtendWith(MockitoExtension.class)
class SlideServiceTest {
    private SlideService slideService;

    @Mock
    private SlideRepository slideRepository;

    @Mock
    private Page<Slide> page;

    @BeforeEach
    void setUp() {
        this.slideService = new SlideService(slideRepository);
    }

    @Test
    @DisplayName("슬라이드 생성")
    void createSlide() {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        String accessLevel = "PUBLIC";
        SlideRequestDto slideRequestDto = new SlideRequestDto(title, subtitle, author, presentedAt, content,
            accessLevel);
        given(slideRepository.save(any(Slide.class))).willReturn(slideRequestDto.toEntity());

        slideService.create(slideRequestDto, 1L);

        verify(slideRepository, times(1)).save(any(Slide.class));
    }

    @Test
    @DisplayName("슬라이드 list 조회")
    void retrieveSlides() {
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
            new Slide(firstTitle, firstSubtitle, firstAuthor, firstPresentedAt, firstContent, AccessLevel.PUBLIC, 1L),
            new Slide(secondTitle, secondSubtitle, secondAuthor, secondPresentedAt, secondContent, AccessLevel.PRIVATE,
                1L));
        given(slideRepository.findAllByAccessLevelAndDeletedAtIsNull(eq(AccessLevel.PUBLIC), any(Pageable.class)))
            .willReturn(page);
        given(page.getContent()).willReturn(slides);

        SlidesRequestDto slidesRequestDto = new SlidesRequestDto(0, 5);
        SlideMetadataDtos slideMetadataDtos = slideService.retrieveAll(slidesRequestDto, null);

        assertAll(
            () -> assertThat(slideMetadataDtos.getSlides().get(0).getTitle()).isEqualTo(firstTitle),
            () -> assertThat(slideMetadataDtos.getSlides().get(1).getTitle()).isEqualTo(secondTitle)
        );
    }

    @Test
    @DisplayName("User 슬라이드 list 조회")
    void retrieveMySlides() {
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
            new Slide(firstTitle, firstSubtitle, firstAuthor, firstPresentedAt, firstContent, AccessLevel.PUBLIC, 1L),
            new Slide(secondTitle, secondSubtitle, secondAuthor, secondPresentedAt, secondContent, AccessLevel.PRIVATE,
                1L));
        given(slideRepository.findAllByUserIdAndDeletedAtIsNullOrderByUpdatedAtDesc(eq(1L), any(Pageable.class)))
            .willReturn(page);
        given(page.getContent()).willReturn(slides);

        SlidesRequestDto slidesRequestDto = new SlidesRequestDto(0, 5);
        SlideMetadataDtos slideMetadataDtos = slideService.retrieveAll(slidesRequestDto, 1L);

        assertAll(
            () -> assertThat(slideMetadataDtos.getSlides().get(0).getTitle()).isEqualTo(firstTitle),
            () -> assertThat(slideMetadataDtos.getSlides().get(1).getTitle()).isEqualTo(secondTitle)
        );
    }

    @Test
    @DisplayName("특정 슬라이드 조회")
    void retrieveSlide() {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        given(slideRepository.findByIdAndDeletedAtIsNull(1L)).willReturn(
            Optional.of(new Slide(title, subtitle, author, presentedAt, content, AccessLevel.PUBLIC)));

        SlideResponseDto slideResponseDto = slideService.retrieve(1L, null);

        assertAll(
            () -> assertThat(slideResponseDto.getContent()).isEqualTo(content),
            () -> assertThat(slideResponseDto.getAccessLevel()).isEqualTo("PUBLIC")
        );
    }

    @Test
    @DisplayName("슬라이드 업데이트")
    void updateSlide() {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        Slide slide = new Slide(title, subtitle, author, presentedAt, content, AccessLevel.PUBLIC, 1L);
        given(slideRepository.findByIdAndDeletedAtIsNull(1L)).willReturn(Optional.of(slide));
        String newTitle = "새 제목";

        SlideRequestDto slideRequestDto = new SlideRequestDto(newTitle, subtitle, author, presentedAt, content,
            "PUBLIC");
        slideService.update(1L, slideRequestDto, 1L);

        verify(slideRepository, times(1)).save(any(Slide.class));
        assertAll(
            () -> assertThat(slide.getTitle()).isEqualTo(newTitle),
            () -> assertThat(slide.getContent()).isEqualTo(content),
            () -> assertThat(slide.getAccessLevel()).isEqualTo(AccessLevel.PUBLIC)
        );
    }

    @Test
    @DisplayName("슬라이드가 존재하지 않을 경우")
    void updateWithInvalidSlide() {
        given(slideRepository.findByIdAndDeletedAtIsNull(1L)).willThrow(new SlideNotFoundException(1L));

        assertThatThrownBy(() -> slideService.update(1L, null, 1L))
            .isInstanceOf(SlideNotFoundException.class)
            .hasMessage("Slide Id : 1 해당 슬라이드는 존재하지 않습니다.");
    }

    @Test
    @DisplayName("자신의 슬라이드가 아닌 경우")
    void updateWithUnauthorizedUser() {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        Slide slide = new Slide(title, subtitle, author, presentedAt, content, AccessLevel.PUBLIC, 1L);
        given(slideRepository.findByIdAndDeletedAtIsNull(1L)).willReturn(Optional.of(slide));

        assertThatThrownBy(() -> slideService.update(1L, null, 2L))
            .isInstanceOf(SlideNotAuthorizedException.class)
            .hasMessage("사용자의 슬라이드가 아닙니다.");
    }

    @Test
    @DisplayName("슬라이드 소프트 삭제")
    void softDeleteSlide() {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        Slide slide = new Slide(title, subtitle, author, presentedAt, content, AccessLevel.PUBLIC, 1L);
        given(slideRepository.findByIdAndDeletedAtIsNull(1L)).willReturn(Optional.of(slide));

        slideService.softDelete(1L, 1L);

        verify(slideRepository, times(1)).save(any(Slide.class));

        assertThat(slide.getDeletedAt()).isNotNull();
    }

    @Test
    void deleteSlideWithUnauthorized() {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        Slide slide = new Slide(title, subtitle, author, presentedAt, content, AccessLevel.PUBLIC, 1L);
        given(slideRepository.findByIdAndDeletedAtIsNull(1L)).willReturn(Optional.of(slide));

        assertThatThrownBy(() -> slideService.softDelete(1L, 2L))
            .isInstanceOf(SlideNotAuthorizedException.class)
            .hasMessage("사용자의 슬라이드가 아닙니다.");
    }

    @Test
    @DisplayName("슬라이드 복제")
    void cloneSlide() {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        Slide slide = new Slide(title, subtitle, author, presentedAt, content, AccessLevel.PUBLIC, 1L);
        given(slideRepository.findByIdAndDeletedAtIsNull(1L)).willReturn(Optional.of(slide));
        given(slideRepository.save(any(Slide.class))).willReturn(slide);

        slideService.clone(1L, 1L);

        verify(slideRepository, times(1)).save(any(Slide.class));
    }
}
