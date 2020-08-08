package dev.minguinho.zeze.domain.slide.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.minguinho.zeze.domain.slide.model.Slide;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlideResponseDtos {
    private List<SlideResponseDto> slides;

    public static SlideResponseDtos from(List<Slide> slides) {
        List<SlideResponseDto> presentationResponses = slides.stream()
            .map(SlideResponseDto::from)
            .collect(Collectors.toList());
        return new SlideResponseDtos(presentationResponses);
    }
}
