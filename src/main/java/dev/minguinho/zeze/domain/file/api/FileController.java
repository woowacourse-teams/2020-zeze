package dev.minguinho.zeze.domain.file.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.file.api.dto.FileUploadRequestDto;
import dev.minguinho.zeze.domain.file.api.dto.FileUrlResponsesDto;
import dev.minguinho.zeze.domain.file.service.FileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {
    private final FileService fileService;

    @PostMapping("/files")
    public ResponseEntity<FileUrlResponsesDto> uploadMultiPartFile(
        @RequestParam("files") List<MultipartFile> files
    ) {
        FileUrlResponsesDto fileUrlResponsesDto = fileService.upload(files);
        return ResponseEntity.ok(fileUrlResponsesDto);
    }

    @PostMapping("/files/external")
    public ResponseEntity<FileUrlResponsesDto> uploadExternalFile(
        @RequestBody FileUploadRequestDto fileUploadRequestDto
    ) throws IOException {
        FileUrlResponsesDto fileUrlResponsesDto = fileService.upload(fileUploadRequestDto);
        return ResponseEntity.ok(fileUrlResponsesDto);
    }
}
