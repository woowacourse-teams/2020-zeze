package dev.minguinho.zeze.slide.dto;

import java.time.ZonedDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
