package dev.minguinho.zeze.domain.slide.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
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
        PageRequest pageRequest = PageRequest.of(slidesRequestDto.getPage(), slidesRequestDto.getSize());
        Page<Slide> slides = getSlides(userId, pageRequest);
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
    public void softDelete(Long slideId, Long userId) {
        Slide persist = findSlideIfAuthorized(slideId, userId);
        persist.delete();
        slideRepository.save(persist);
    }

    private Page<Slide> getSlides(Long userId, PageRequest pageRequest) {
        if (Objects.isNull(userId)) {
            return slideRepository.findAllByAccessLevelAndDeletedAtIsNull(Slide.AccessLevel.PUBLIC, pageRequest);
        }
        return slideRepository.findAllByUserIdAndDeletedAtIsNullOrderByUpdatedAtDesc(userId, pageRequest);
    }

    private Slide getSlide(Long slideId, Long userId) {
        Slide slide = slideRepository.findById(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        validateDeletedSlide(slide);
        if (Objects.isNull(userId) || !slide.isOwner(userId)) {
            validatePublic(slide);
        }
        return slide;
    }

    private void validatePublic(Slide slide) {
        if (!slide.isPublic()) {
            throw new SlideNotAuthorizedException();
        }
    }

    private Slide findSlideIfAuthorized(Long slideId, Long userId) {
        Slide slide = slideRepository.findById(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        validateDeletedSlide(slide);
        if (!slide.isOwner(userId)) {
            throw new SlideNotAuthorizedException();
        }
        return slide;
    }

    private void validateDeletedSlide(Slide slide) {
        if(slide.isDeleted()) {
            throw new SlideNotFoundException(slide.getId());
        }
    }
}
