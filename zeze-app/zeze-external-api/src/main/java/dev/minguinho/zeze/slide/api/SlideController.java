package dev.minguinho.zeze.slide.api;

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

import dev.minguinho.zeze.auth.config.Secured;
import dev.minguinho.zeze.slide.dto.SlideMetadataDtos;
import dev.minguinho.zeze.slide.dto.SlideRequestDto;
import dev.minguinho.zeze.slide.dto.SlideResponseDto;
import dev.minguinho.zeze.slide.dto.SlidesRequestDto;
import dev.minguinho.zeze.slide.service.SlideService;
import dev.minguinho.zeze.user.config.LoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/slides")
public class SlideController {
    private final SlideService slideService;

    @PostMapping
    @Secured
    public ResponseEntity<Void> createSlide(
        @RequestBody SlideRequestDto slideRequestDto,
        @LoginUserId Long userId
    ) {
        Long slideId = slideService.create(slideRequestDto, userId);
        return ResponseEntity.created(URI.create("/api/slides/" + slideId)).build();
    }

    @GetMapping
    public ResponseEntity<SlideMetadataDtos> retrieveSlides(
        @ModelAttribute SlidesRequestDto slidesRequestDto,
        @LoginUserId Long userId
    ) {
        SlideMetadataDtos slideMetadataDtos = slideService.retrieveAll(slidesRequestDto, userId);
        return ResponseEntity.ok(slideMetadataDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlideResponseDto> retrieveSlide(
        @PathVariable("id") Long slideId,
        @LoginUserId Long userId
    ) {
        SlideResponseDto slideResponseDto = slideService.retrieve(slideId, userId);
        return ResponseEntity.ok(slideResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSlide(
        @PathVariable("id") Long slideId,
        @RequestBody SlideRequestDto slideRequestDto,
        @LoginUserId Long userId
    ) {
        slideService.update(slideId, slideRequestDto, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlide(
        @PathVariable("id") Long slideId,
        @LoginUserId Long userId
    ) {
        slideService.softDelete(slideId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/own")
    public ResponseEntity<Boolean> ownSlide(
        @PathVariable("id") Long slideId,
        @LoginUserId Long userId
    ) {
        Boolean ownedBy = slideService.checkSlideOwnedBy(slideId, userId);
        return ResponseEntity.ok(ownedBy);
    }

    @PostMapping("/{id}")
    @Secured
    public ResponseEntity<Void> cloneSlide(
        @PathVariable("id") Long slideId,
        @LoginUserId Long userId
    ) {
        Long newSlideId = slideService.clone(slideId, userId);
        return ResponseEntity.created(URI.create("/api/slides/" + newSlideId)).build();
    }
}
