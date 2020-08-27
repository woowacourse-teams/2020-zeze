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
public class SlideMetadataDtos {
    private List<SlideMetadataDto> slides;
    private int totalPage;

    public static SlideMetadataDtos from(Page<Slide> slides) {
        List<SlideMetadataDto> slideResponses = slides.getContent()
            .stream()
            .map(SlideMetadataDto::from)
            .collect(Collectors.toList());
        return new SlideMetadataDtos(slideResponses, slides.getTotalPages());
    }
}
