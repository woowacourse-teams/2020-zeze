package dev.minguinho.zeze.domain.slide.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

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
    private int totalPage;

    public static SlideResponseDtos from(Page<Slide> slides) {
        List<SlideResponseDto> slideResponses = slides.getContent()
            .stream()
            .map(SlideResponseDto::from)
            .collect(Collectors.toList());
        return new SlideResponseDtos(slideResponses, slides.getTotalPages());
    }
}
