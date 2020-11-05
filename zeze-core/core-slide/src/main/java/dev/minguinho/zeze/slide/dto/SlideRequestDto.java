package dev.minguinho.zeze.slide.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlideRequestDto {
    private String title;
    private String subtitle;
    private String author;
    private String presentedAt;
    private String content;
    private String accessLevel;
}
