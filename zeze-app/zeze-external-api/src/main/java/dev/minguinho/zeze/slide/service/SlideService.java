package dev.minguinho.zeze.slide.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.slide.dto.SlideMetadataDtos;
import dev.minguinho.zeze.slide.dto.SlideRequestDto;
import dev.minguinho.zeze.slide.dto.SlideResponseDto;
import dev.minguinho.zeze.slide.dto.SlidesRequestDto;
import dev.minguinho.zeze.slide.exception.SlideNotAuthorizedException;
import dev.minguinho.zeze.slide.exception.SlideNotFoundException;
import dev.minguinho.zeze.slide.infra.SlideRepository;
import dev.minguinho.zeze.slide.model.Slide;

@Service
@RequiredArgsConstructor
public class SlideService {
    private final SlideRepository slideRepository;

    @Transactional
    public Long create(SlideRequestDto slideRequestDto, Long userId) {
        Slide slide = SlideConvertor.toEntity(slideRequestDto, userId);
        Slide persist = slideRepository.save(slide);
        return persist.getId();
    }

    public SlideMetadataDtos retrieveAll(SlidesRequestDto slidesRequestDto, Long userId) {
        PageRequest pageRequest = PageRequest.of(slidesRequestDto.getPage(), slidesRequestDto.getSize());
        Page<Slide> slides = getSlides(userId, pageRequest);
        return SlideConvertor.toSlideMetadataDtos(slides);
    }

    public SlideResponseDto retrieve(Long slideId, Long userId) {
        Slide slide = getSlide(slideId, userId);
        return SlideConvertor.toResponseDto(slide);
    }

    @Transactional
    public void update(Long slideId, SlideRequestDto slideRequestDto, Long userId) {
        Slide persist = findSlideIfAuthorized(slideId, userId);
        persist.update(SlideConvertor.toEntity(slideRequestDto));
        slideRepository.save(persist);
    }

    @Transactional
    public void softDelete(Long slideId, Long userId) {
        Slide persist = findSlideIfAuthorized(slideId, userId);
        persist.delete();
        slideRepository.save(persist);
    }

    @Transactional
    public Boolean checkSlideOwnedBy(Long slideId, Long userId) {
        Slide slide = slideRepository.findById(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        return slide.isOwner(userId);
    }

    @Transactional
    public Long clone(Long slideId, Long userId) {
        Slide slide = getSlide(slideId, userId);
        Slide persist = slideRepository.save(slide.clone(userId));
        return persist.getId();
    }

    private Page<Slide> getSlides(Long userId, PageRequest pageRequest) {
        if (Objects.isNull(userId)) {
            return slideRepository.findAllByAccessLevelAndDeletedAtIsNullOrderByUpdatedAtDesc(Slide.AccessLevel.PUBLIC,
                pageRequest);
        }
        return slideRepository.findAllByUserIdAndDeletedAtIsNullOrderByUpdatedAtDesc(userId, pageRequest);
    }

    private Slide getSlide(Long slideId, Long userId) {
        Slide slide = slideRepository.findByIdAndDeletedAtIsNull(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
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
        Slide slide = slideRepository.findByIdAndDeletedAtIsNull(slideId)
            .orElseThrow(() -> new SlideNotFoundException(slideId));
        if (!slide.isOwner(userId)) {
            throw new SlideNotAuthorizedException();
        }
        return slide;
    }
}
