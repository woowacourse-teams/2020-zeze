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

@DataJpaTest
class SlideRepositoryTest {
    @Autowired
    private SlideRepository slideRepository;

    @Test
    @DisplayName("슬라이드 저장")
    void save() {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        Slide slide = new Slide(title, subtitle, author, presentedAt, content, AccessLevel.PUBLIC, 1L);

        Slide persist = slideRepository.save(slide);

        assertThat(persist.getId()).isNotNull();
    }

    @Test
    @DisplayName("슬라이드 list 조회")
    void findAllByUserIdAndDeletedAtOrderByUpdatedAtDesc() {
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
        slideRepository.saveAll(slides);

        PageRequest pageRequest = PageRequest.of(0, 5);
        List<Slide> persistPresentations = slideRepository.findAllByUserIdAndDeletedAtIsNullOrderByUpdatedAtDesc(1L, pageRequest)
            .getContent();

        assertAll(
            () -> assertThat(persistPresentations).hasSize(2),
            () -> assertThat(persistPresentations.get(0).getTitle()).isEqualTo(secondTitle),
            () -> assertThat(persistPresentations.get(1).getTitle()).isEqualTo(firstTitle),
            () -> assertThat(persistPresentations.get(0).getDeletedAt()).isNull(),
            () -> assertThat(persistPresentations.get(1).getDeletedAt()).isNull()
        );
    }

    @Test
    @DisplayName("슬라이드 내용 변경")
    void update() {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        Slide slide = new Slide(title, subtitle, author, presentedAt, content, AccessLevel.PUBLIC, 1L);
        Slide persist = slideRepository.save(slide);

        String newTitle = "새 제목";
        Slide request = new Slide(newTitle, subtitle, author, presentedAt, content, AccessLevel.PUBLIC);
        persist.update(request);
        Slide newSlide = slideRepository.save(persist);

        assertAll(
            () -> assertThat(newSlide.getTitle()).isEqualTo(newTitle),
            () -> assertThat(newSlide.getContent()).isEqualTo(content),
            () -> assertThat(newSlide.getAccessLevel()).isEqualTo(AccessLevel.PUBLIC)
        );
    }

    @Test
    @DisplayName("슬라이드 소프트 삭제")
    void updateDeletedAt() {
        String title = "제목";
        String subtitle = "부제목";
        String author = "작성자";
        String presentedAt = "2020-07-21";
        String content = "내용";
        Slide slide = new Slide(title, subtitle, author, presentedAt, content, AccessLevel.PUBLIC, 1L);

        slide.delete();
        Slide deletedSlide = slideRepository.save(slide);

        assertThat(deletedSlide.getDeletedAt()).isNotNull();
    }
}
