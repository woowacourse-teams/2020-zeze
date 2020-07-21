package dev.minguinho.zeze.domain.file.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.minguinho.zeze.domain.file.api.dto.FileUploadRequestDto;
import dev.minguinho.zeze.domain.file.api.dto.FileUrlResponses;
import dev.minguinho.zeze.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PresentationController {
    private final FileService fileService;

    @PostMapping("/files")
    public ResponseEntity<FileUrlResponses> uploadFile(
        @ModelAttribute FileUploadRequestDto fileUploadRequestDto
    ) {
        FileUrlResponses fileUrlResponses = fileService.upload(fileUploadRequestDto.getFiles());
        return ResponseEntity.ok(fileUrlResponses);
    }
}
