package dev.minguinho.zeze.slide.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import dev.minguinho.zeze.slide.dto.SlideMetadataDto;
import dev.minguinho.zeze.slide.dto.SlideMetadataDtos;
import dev.minguinho.zeze.slide.dto.SlideRequestDto;
import dev.minguinho.zeze.slide.dto.SlideResponseDto;
import dev.minguinho.zeze.slide.model.Slide;

public class SlideConvertor {
    public static Slide toEntity(SlideRequestDto slideRequestDto, Long userId) {
        return new Slide(
            slideRequestDto.getTitle(),
            slideRequestDto.getSubtitle(),
            slideRequestDto.getAuthor(),
            slideRequestDto.getPresentedAt(),
            slideRequestDto.getContent(),
            Slide.AccessLevel.valueOf(slideRequestDto.getAccessLevel()),
            userId);
    }

    public static Slide toEntity(SlideRequestDto slideRequestDto) {
        return new Slide(
            slideRequestDto.getTitle(),
            slideRequestDto.getSubtitle(),
            slideRequestDto.getAuthor(),
            slideRequestDto.getPresentedAt(),
            slideRequestDto.getContent(),
            Slide.AccessLevel.valueOf(slideRequestDto.getAccessLevel()));
    }

    public static SlideResponseDto toResponseDto(Slide slide) {
        return new SlideResponseDto(slide.getContent(), slide.getAccessLevel().name(), slide.getUpdatedAt());
    }

    public static SlideMetadataDtos toSlideMetadataDtos(Page<Slide> slides) {
        List<SlideMetadataDto> slideResponses = slides.getContent()
            .stream()
            .map(SlideConvertor::toSlideMetadataDto)
            .collect(Collectors.toList());
        return new SlideMetadataDtos(slideResponses, slides.getTotalPages());
    }

    public static SlideMetadataDto toSlideMetadataDto(Slide slide) {
        return new SlideMetadataDto(
            slide.getId(),
            slide.getTitle(),
            slide.getSubtitle(),
            slide.getAuthor(),
            slide.getPresentedAt(),
            slide.getCreatedAt(),
            slide.getUpdatedAt());
    }
}
