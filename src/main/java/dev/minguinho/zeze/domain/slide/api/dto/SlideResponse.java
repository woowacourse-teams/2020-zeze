package dev.minguinho.zeze.domain.slide.api.dto;

import java.time.ZonedDateTime;

import dev.minguinho.zeze.domain.slide.model.Slide;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SlideResponse {
    private Long id;
    private String title;
    private String content;
    private String contentType;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static SlideResponse from(Slide slide) {
        return new SlideResponse(slide.getId(), slide.getTitle(), slide.getContent(), slide.getContentType(),
            slide.getCreatedAt(), slide.getUpdatedAt());
    }
}
