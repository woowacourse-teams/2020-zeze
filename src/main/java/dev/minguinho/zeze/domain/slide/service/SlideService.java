package dev.minguinho.zeze.domain.slide.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.minguinho.zeze.domain.slide.api.dto.SlideRequest;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponses;
import dev.minguinho.zeze.domain.slide.exception.SlideNotFoundException;
import dev.minguinho.zeze.domain.slide.model.Slide;
import dev.minguinho.zeze.domain.slide.model.SlideRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlideService {
    private final SlideRepository slideRepository;

    @Transactional
    public void createSlide(SlideRequest slideRequest) {
        Slide slide = new Slide(slideRequest.getTitle(), slideRequest.getContent(), slideRequest.getContentType());
        slideRepository.save(slide);
    }

    public SlideResponses retrieveSlides() {
        List<Slide> slides = slideRepository.findAll();
        return SlideResponses.from(slides);
    }

    @Transactional
    public void updateSlide(Long slideId, SlideRequest slideRequest) {
        Slide persist = slideRepository.findById(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        persist.update(slideRequest.getTitle(), slideRequest.getContent(), slideRequest.getContentType());
        slideRepository.save(persist);
    }
}
