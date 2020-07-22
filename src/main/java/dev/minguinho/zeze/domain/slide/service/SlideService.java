package dev.minguinho.zeze.domain.slide.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.minguinho.zeze.domain.slide.api.dto.SlideRequest;
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
}
