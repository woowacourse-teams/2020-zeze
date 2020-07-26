package dev.minguinho.zeze.domain.slide.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SlideRepositoryTest {
    @Autowired
    private SlideRepository slideRepository;

    @Test
    @DisplayName("슬라이드 저장")
    void save() {
        String title = "제목";
        String content = "내용";
        String contentType = "타입";
        Slide slide = new Slide(title, content, contentType);

        Slide persist = slideRepository.save(slide);

        assertThat(persist.getId()).isNotNull();
    }

    @Test
    @DisplayName("슬리이드 전체 조회")
    void findAll() {
        String firstTitle = "제목1";
        String firstContent = "내용1";
        String firstContentType = "타입1";
        String secondTitle = "제목2";
        String secondContent = "내용2";
        String secondContentType = "타입2";
        List<Slide> slides = Arrays.asList(new Slide(firstTitle, firstContent, firstContentType),
            new Slide(secondTitle, secondContent, secondContentType));
        slideRepository.saveAll(slides);

        List<Slide> persistPresentations = slideRepository.findAll();

        assertAll(
            () -> assertThat(persistPresentations).hasSize(2),
            () -> assertThat(persistPresentations.get(0).getTitle()).isEqualTo(firstTitle),
            () -> assertThat(persistPresentations.get(1).getTitle()).isEqualTo(secondTitle)
        );
    }

    @Test
    @DisplayName("슬라이드 내용 변경")
    void update() {
        String title = "제목";
        String content = "내용";
        String contentType = "타입";
        Slide slide = new Slide(title, content, contentType);
        Slide persist = slideRepository.save(slide);

        String newTitle = "새 제목";
        persist.update(newTitle, null, null);
        Slide newSlide = slideRepository.save(persist);

        assertAll(
            () -> assertThat(newSlide.getTitle()).isEqualTo(newTitle),
            () -> assertThat(newSlide.getContent()).isEqualTo(content),
            () -> assertThat(newSlide.getContentType()).isEqualTo(contentType)
        );
    }
}
