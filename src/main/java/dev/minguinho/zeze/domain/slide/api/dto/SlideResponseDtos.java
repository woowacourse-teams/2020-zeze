package dev.minguinho.zeze.domain.slide.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import dev.minguinho.zeze.domain.slide.model.Slide;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SlideResponseDtos {
    private List<SlideResponseDto> values;

    public static SlideResponseDtos from(List<Slide> presentations) {
        List<SlideResponseDto> presentationResponses = presentations.stream()
            .map(SlideResponseDto::from)
            .collect(Collectors.toList());
        return new SlideResponseDtos(presentationResponses);
    }
}
