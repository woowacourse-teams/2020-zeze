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
import dev.minguinho.zeze.domain.slide.exception.SlideNotAuthorizedException;
import dev.minguinho.zeze.domain.slide.exception.SlideNotFoundException;
import dev.minguinho.zeze.domain.slide.model.Slide;
import dev.minguinho.zeze.domain.slide.model.SlideRepository;

@Service
@RequiredArgsConstructor
public class SlideService {
    public static final int FIRST_PAGE = 0;

    private final SlideRepository slideRepository;

    @Transactional
    public Long createSlide(SlideRequestDto slideRequestDto, Long userId) {
        Slide slide = slideRequestDto.toEntity(userId);
        Slide persist = slideRepository.save(slide);
        return persist.getId();
    }

    public SlideResponseDtos retrieveSlides(SlidesRequestDto slidesRequestDto) {
        PageRequest pageRequest = PageRequest.of(FIRST_PAGE, slidesRequestDto.getSize());
        List<Slide> slides = slideRepository.findAllByAccessLevel(Slide.AccessLevel.PUBLIC, pageRequest)
            .getContent();
        return SlideResponseDtos.from(slides);
    }

    public SlideResponseDtos retrieveSlides(SlidesRequestDto slidesRequestDto, Long userId) {
        PageRequest pageRequest = PageRequest.of(FIRST_PAGE, slidesRequestDto.getSize());
        List<Slide> slides = slideRepository.findAllByUserIdAndIdGreaterThan(userId, slidesRequestDto.getId(),
            pageRequest).getContent();
        return SlideResponseDtos.from(slides);
    }

    public SlideResponseDto retrieveSlide(Long slideId) {
        Slide slide = slideRepository.findById(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        return SlideResponseDto.from(slide);
    }

    @Transactional
    public void updateSlide(Long slideId, SlideRequestDto slideRequestDto, Long userId) {
        Slide persist = findSlideIfAuthorized(slideId, userId);
        persist.update(slideRequestDto.toEntity());
        slideRepository.save(persist);
    }

    @Transactional
    public void deleteSlide(Long slideId, Long userId) {
        Slide persist = findSlideIfAuthorized(slideId, userId);
        slideRepository.delete(persist);
    }

    private Slide findSlideIfAuthorized(Long slideId, Long userId) {
        Slide persist = slideRepository.findById(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        if (persist.isNotOwner(userId)) {
            throw new SlideNotAuthorizedException();
        }
        return persist;
    }
}
