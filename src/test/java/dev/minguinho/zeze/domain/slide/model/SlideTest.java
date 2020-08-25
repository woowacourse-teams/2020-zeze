package dev.minguinho.zeze.domain.slide.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SlideTest {
    @Test
    @DisplayName("deleteAt 변경 테스트")
    void updateDeletedAt() {
        Slide slide = new Slide();
        slide.delete();
        assertThat(slide.getDeletedAt()).isNotNull();
    }
}
