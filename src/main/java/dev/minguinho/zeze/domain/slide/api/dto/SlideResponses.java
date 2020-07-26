package dev.minguinho.zeze.domain.slide.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import dev.minguinho.zeze.domain.slide.model.Slide;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SlideResponses {
    private List<SlideResponse> values;

    public static SlideResponses from(List<Slide> presentations) {
        List<SlideResponse> presentationResponses = presentations.stream()
            .map(SlideResponse::from)
            .collect(Collectors.toList());
        return new SlideResponses(presentationResponses);
    }
}
