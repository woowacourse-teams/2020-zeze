package dev.minguinho.zeze.slide.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class SlideTest {
    @Test
    @DisplayName("deleteAt 변경 테스트")
    public void updateDeletedAt() {
        Slide slide = new Slide();
        slide.delete();
        assertThat(slide.getDeletedAt()).isNotNull();
    }
}
