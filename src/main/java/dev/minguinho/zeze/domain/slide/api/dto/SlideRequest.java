package dev.minguinho.zeze.domain.slide.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SlideRequest {
    private String title;
    private String content;
    private String contentType;
}
