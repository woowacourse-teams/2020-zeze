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
public class SlideMetadataDto {
    private Long id;
    private String title;
    private String subtitle;
    private String author;
    private String presentedAt;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static SlideMetadataDto from(Slide slide) {
        return new SlideMetadataDto(slide.getId(), slide.getTitle(), slide.getSubtitle(), slide.getAuthor(),
            slide.getPresentedAt(), slide.getCreatedAt(), slide.getUpdatedAt());
    }
}
