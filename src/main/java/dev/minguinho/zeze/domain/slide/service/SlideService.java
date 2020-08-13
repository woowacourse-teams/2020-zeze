package dev.minguinho.zeze.domain.slide.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.auth.exception.NotAuthorizedException;
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
    public Long create(SlideRequestDto slideRequestDto, Long userId) {
        if (Objects.isNull(userId)) {
            throw new NotAuthorizedException();
        }
        Slide slide = slideRequestDto.toEntity(userId);
        Slide persist = slideRepository.save(slide);
        return persist.getId();
    }

    public SlideResponseDtos retrieveAll(SlidesRequestDto slidesRequestDto, Long userId) {
        PageRequest pageRequest = PageRequest.of(FIRST_PAGE, slidesRequestDto.getSize());
        List<Slide> slides = getSlides(slidesRequestDto, userId, pageRequest);
        return SlideResponseDtos.from(slides);
    }

    public SlideResponseDto retrieve(Long slideId, Long userId) {
        Slide slide = getSlide(slideId, userId);
        return SlideResponseDto.from(slide);
    }

    @Transactional
    public void update(Long slideId, SlideRequestDto slideRequestDto, Long userId) {
        Slide persist = findSlideIfAuthorized(slideId, userId);
        persist.update(slideRequestDto.toEntity());
        slideRepository.save(persist);
    }

    @Transactional
    public void delete(Long slideId, Long userId) {
        Slide persist = findSlideIfAuthorized(slideId, userId);
        slideRepository.delete(persist);
    }

    private List<Slide> getSlides(SlidesRequestDto slidesRequestDto, Long userId, PageRequest pageRequest) {
        if (Objects.isNull(userId)) {
            return slideRepository.findAllByAccessLevel(Slide.AccessLevel.PUBLIC, pageRequest).getContent();
        }
        return slideRepository.findAllByUserIdAndIdGreaterThan(userId, slidesRequestDto.getId(),
            pageRequest).getContent();
    }

    private Slide getSlide(Long slideId, Long userId) {
        if (Objects.isNull(userId)) {
            return findSlideIfPublic(slideId);
        }
        return findSlideIfAuthorized(slideId, userId);
    }

    private Slide findSlideIfAuthorized(Long slideId, Long userId) {
        Slide slide = slideRepository.findById(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        if (!slide.isOwner(userId)) {
            throw new SlideNotAuthorizedException();
        }
        return slide;
    }

    private Slide findSlideIfPublic(Long slideId) {
        Slide slide = slideRepository.findById(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        if (!slide.isPublic()) {
            throw new SlideNotAuthorizedException();
        }
        return slide;
    }
}
