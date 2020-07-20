package dev.minguinho.zeze.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.minguinho.zeze.common.DefaultResponseEntity;
import dev.minguinho.zeze.controller.dto.FileUploadRequestDto;
import dev.minguinho.zeze.controller.dto.FileUrlResponses;
import dev.minguinho.zeze.service.PresentationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PresentationController {
    private final PresentationService presentationService;

    @PostMapping("/files")
    public ResponseEntity<DefaultResponseEntity<FileUrlResponses>> uploadFile(
        @ModelAttribute FileUploadRequestDto fileUploadRequestDto) {
        FileUrlResponses fileUrlResponses = presentationService.upload(fileUploadRequestDto.getFiles());
        return ResponseEntity.ok(DefaultResponseEntity.from(fileUrlResponses));
    }
}
