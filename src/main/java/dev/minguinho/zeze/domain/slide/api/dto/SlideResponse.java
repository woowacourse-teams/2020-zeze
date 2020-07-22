package dev.minguinho.zeze.domain.slide.api.dto;

import java.time.ZonedDateTime;

import dev.minguinho.zeze.domain.slide.model.Slide;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SlideResponse {
    private final String title;
    private final String content;
    private final String contentType;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;

    public static SlideResponse from(Slide slide) {
        return new SlideResponse(slide.getTitle(), slide.getContent(), slide.getContentType(),
            slide.getCreatedAt(), slide.getUpdatedAt());
    }
}
