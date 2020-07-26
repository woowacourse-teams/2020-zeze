package dev.minguinho.zeze.domain.slide.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.minguinho.zeze.domain.slide.api.dto.SlideRequest;
import dev.minguinho.zeze.domain.slide.api.dto.SlideResponses;
import dev.minguinho.zeze.domain.slide.service.SlideService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SlideController {
    private final SlideService slideService;

    @PostMapping("/slides")
    public ResponseEntity<Void> createSlide(
        @RequestBody SlideRequest slideRequest
    ) {
        slideService.createSlide(slideRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/slides")
    public ResponseEntity<SlideResponses> retrieveSlides() {
        SlideResponses slideResponses = slideService.retrieveSlides();
        return ResponseEntity.ok(slideResponses);
    }

    @PatchMapping("/slides/{id}")
    public ResponseEntity<Void> updateSlide(
        @PathVariable("id") Long slideId,
        @RequestBody SlideRequest slideRequest
    ) {
        slideService.updateSlide(slideId, slideRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/slides/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable("id") Long slideId) {
        slideService.deleteSlide(slideId);
        return ResponseEntity.noContent().build();
    }
}
