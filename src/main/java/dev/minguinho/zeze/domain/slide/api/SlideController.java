package dev.minguinho.zeze.domain.slide.api;

import java.net.URI;

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

import dev.minguinho.zeze.domain.auth.config.Secured;
import dev.minguinho.zeze.domain.slide.api.dto.SlideRequestDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDto;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponseDtos;
import dev.minguinho.zeze.domain.slide.api.dto.SlidesRequestDto;
import dev.minguinho.zeze.domain.slide.service.SlideService;
import dev.minguinho.zeze.domain.user.config.LoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/slides")
public class SlideController {
    private final SlideService slideService;

    @Secured
    @PostMapping
    public ResponseEntity<Void> createSlide(
        @RequestBody SlideRequestDto slideRequestDto,
        @LoginUserId Long userId
    ) {
        Long slideId = slideService.createSlide(slideRequestDto, userId);
        return ResponseEntity.created(URI.create("/api/slides/" + slideId)).build();
    }

    @Secured
    @GetMapping
    public ResponseEntity<SlideResponseDtos> retrieveSlides(
        @ModelAttribute SlidesRequestDto slidesRequestDto,
        @LoginUserId Long userId
    ) {
        SlideResponseDtos slideResponseDtos = slideService.retrieveSlides(slidesRequestDto, userId);
        return ResponseEntity.ok(slideResponseDtos);
    }

    @Secured
    @GetMapping("/{id}")
    public ResponseEntity<SlideResponseDto> retrieveSlide(@PathVariable("id") Long slideId) {
        SlideResponseDto slideResponseDto = slideService.retrieveSlide(slideId);
        return ResponseEntity.ok(slideResponseDto);
    }

    @Secured
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSlide(
        @PathVariable("id") Long slideId,
        @RequestBody SlideRequestDto slideRequestDto
    ) {
        slideService.updateSlide(slideId, slideRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Secured
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable("id") Long slideId) {
        slideService.deleteSlide(slideId);
        return ResponseEntity.noContent().build();
    }
}
