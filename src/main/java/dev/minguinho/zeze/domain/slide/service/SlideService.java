package dev.minguinho.zeze.domain.slide.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.slide.api.dto.SlideRequestDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDtos;
import dev.minguinho.zeze.domain.slide.api.dto.SlidesRequestDto;
import dev.minguinho.zeze.domain.slide.exception.SlideNotFoundException;
import dev.minguinho.zeze.domain.slide.model.Slide;
import dev.minguinho.zeze.domain.slide.model.SlideRepository;

@Service
@RequiredArgsConstructor
public class SlideService {
    public static final int FIRST_PAGE = 0;
    private final SlideRepository slideRepository;

    @Transactional
    public Long createSlide(SlideRequestDto slideRequestDto) {
        Slide slide = slideRequestDto.toEntity();
        Slide persist = slideRepository.save(slide);
        return persist.getId();
    }

    public SlideResponseDtos retrieveSlides(SlidesRequestDto slidesRequestDto) {
        PageRequest pageRequest = PageRequest.of(FIRST_PAGE, slidesRequestDto.getSize());
        List<Slide> slides = slideRepository.findAllByIdGreaterThan(slidesRequestDto.getId(), pageRequest)
            .getContent();
        return SlideResponseDtos.from(slides);
    }

    public SlideResponseDto retrieveSlide(Long slideId) {
        Slide slide = slideRepository.findById(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        return SlideResponseDto.from(slide);
    }

    @Transactional
    public void updateSlide(Long slideId, SlideRequestDto slideRequestDto) {
        Slide persist = slideRepository.findById(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        persist.update(slideRequestDto.getTitle(), slideRequestDto.getContent(), slideRequestDto.getAccessLevel());
        slideRepository.save(persist);
    }

    @Transactional
    public void deleteSlide(Long slideId) {
        slideRepository.deleteById(slideId);
    }
}
