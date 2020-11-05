package dev.minguinho.zeze.slide.dto;

import java.time.ZonedDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlideResponseDto {
    private String content;
    private String accessLevel;
    private ZonedDateTime updatedAt;
}
