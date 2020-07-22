package dev.minguinho.zeze.domain.slide.model;

import static org.assertj.core.api.Assertions.*;

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
}
