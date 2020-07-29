package dev.minguinho.zeze.domain.file.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.file.api.dto.FileUrlResponses;
import dev.minguinho.zeze.domain.file.service.FileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {
    private final FileService fileService;

    @PostMapping("/files")
    public ResponseEntity<FileUrlResponses> uploadFile(
        @RequestParam("files") List<MultipartFile> files
    ) {
        FileUrlResponses fileUrlResponses = fileService.upload(files);
        return ResponseEntity.ok(fileUrlResponses);
    }
}
