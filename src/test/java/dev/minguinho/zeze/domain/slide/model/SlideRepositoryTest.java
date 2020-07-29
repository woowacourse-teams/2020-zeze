package dev.minguinho.zeze.domain.slide.model;

import static dev.minguinho.zeze.domain.slide.model.Slide.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import dev.minguinho.zeze.domain.slide.exception.SlideNotFoundException;

@DataJpaTest
class SlideRepositoryTest {
    @Autowired
    private SlideRepository slideRepository;

    @Test
    @DisplayName("슬라이드 저장")
    void save() {
        String title = "제목";
        String content = "내용";
        Slide slide = new Slide(title, content, AccessLevel.PUBLIC);

        Slide persist = slideRepository.save(slide);

        assertThat(persist.getId()).isNotNull();
    }

    @Test
    @DisplayName("슬리이드 list 조회")
    void findByIdGreaterThan() {
        String firstTitle = "제목1";
        String firstContent = "내용1";
        String secondTitle = "제목2";
        String secondContent = "내용2";
        List<Slide> slides = Arrays.asList(new Slide(firstTitle, firstContent, AccessLevel.PUBLIC),
            new Slide(secondTitle, secondContent, AccessLevel.PRIVATE));
        slideRepository.saveAll(slides);

        PageRequest pageRequest = PageRequest.of(0, 5);
        List<Slide> persistPresentations = slideRepository.findAllByIdGreaterThan(0L, pageRequest).getContent();

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
        Slide slide = new Slide(title, content, AccessLevel.PUBLIC);
        Slide persist = slideRepository.save(slide);

        String newTitle = "새 제목";
        persist.update(newTitle, null, null);
        Slide newSlide = slideRepository.save(persist);

        assertAll(
            () -> assertThat(newSlide.getTitle()).isEqualTo(newTitle),
            () -> assertThat(newSlide.getContent()).isEqualTo(content),
            () -> assertThat(newSlide.getAccessLevel()).isEqualTo(AccessLevel.PUBLIC)
        );
    }

    @Test
    @DisplayName("슬라이드 삭제")
    void delete() {
        String title = "제목";
        String content = "내용";
        Slide slide = new Slide(title, content, AccessLevel.PUBLIC);
        Slide persist = slideRepository.save(slide);

        slideRepository.deleteById(persist.getId());

        assertThatThrownBy(() -> slideRepository.findById(persist.getId())
            .orElseThrow(() -> new SlideNotFoundException(persist.getId()))
        )
            .isInstanceOf(SlideNotFoundException.class)
            .hasMessageContaining("해당 슬라이드는 존재하지 않습니다.");
    }
}
