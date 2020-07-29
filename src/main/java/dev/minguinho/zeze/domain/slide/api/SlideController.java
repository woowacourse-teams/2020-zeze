package dev.minguinho.zeze.domain.slide.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.slide.api.dto.SlideRequestDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDtos;
import dev.minguinho.zeze.domain.slide.api.dto.SlidesRequestDto;
import dev.minguinho.zeze.domain.slide.service.SlideService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SlideController {
    private final SlideService slideService;

    @PostMapping("/slides")
    public ResponseEntity<Void> createSlide(
        @RequestBody SlideRequestDto slideRequestDto
    ) throws URISyntaxException {
        Long slideId = slideService.createSlide(slideRequestDto);
        return ResponseEntity.created(new URI("/api/slides/" + slideId)).build();
    }

    @GetMapping("/slides")
    public ResponseEntity<SlideResponseDtos> retrieveSlides(
        @ModelAttribute SlidesRequestDto slidesRequestDto
    ) {
        SlideResponseDtos slideResponseDtos = slideService.retrieveSlides(slidesRequestDto);
        return ResponseEntity.ok(slideResponseDtos);
    }

    @GetMapping("/slides/{id}")
    public ResponseEntity<SlideResponseDto> retrieveSlide(@PathVariable("id") Long slideId) {
        SlideResponseDto slideResponseDto = slideService.retrieveSlide(slideId);
        return ResponseEntity.ok(slideResponseDto);
    }

    @PatchMapping("/slides/{id}")
    public ResponseEntity<Void> updateSlide(
        @PathVariable("id") Long slideId,
        @RequestBody SlideRequestDto slideRequestDto
    ) {
        slideService.updateSlide(slideId, slideRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/slides/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable("id") Long slideId) {
        slideService.deleteSlide(slideId);
        return ResponseEntity.noContent().build();
    }
}
