package dev.minguinho.zeze.domain.slide.api.dto;

import java.time.ZonedDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.minguinho.zeze.domain.slide.model.Slide;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlideResponseDto {
    private String content;
    private String accessLevel;
    private ZonedDateTime updatedAt;

    public static SlideResponseDto from(Slide slide) {
        return new SlideResponseDto(slide.getContent(), slide.getAccessLevel().name(), slide.getUpdatedAt());
    }
}
