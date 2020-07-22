package dev.minguinho.zeze.domain.slide.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.minguinho.zeze.domain.slide.api.dto.SlideRequest;
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
}
