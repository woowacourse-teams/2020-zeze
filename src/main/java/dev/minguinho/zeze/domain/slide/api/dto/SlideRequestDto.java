package dev.minguinho.zeze.domain.slide.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.minguinho.zeze.domain.slide.model.Slide;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlideRequestDto {
    private String title;
    private String content;
    private String accessLevel;

    public Slide toEntity() {
        return new Slide(title, content, Slide.AccessLevel.valueOf(accessLevel));
    }
}
