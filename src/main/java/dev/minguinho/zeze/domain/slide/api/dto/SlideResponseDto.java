package dev.minguinho.zeze.domain.slide.api.dto;

import java.time.ZonedDateTime;

import dev.minguinho.zeze.domain.slide.model.Slide;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlideResponseDto {
    private Long id;
    private String title;
    private String content;
    private String accessLevel;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static SlideResponseDto from(Slide slide) {
        return new SlideResponseDto(slide.getId(), slide.getTitle(), slide.getContent(), slide.getAccessLevel().name(),
            slide.getCreatedAt(), slide.getUpdatedAt());
    }
}
