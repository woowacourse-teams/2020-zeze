package dev.minguinho.zeze.domain.file.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.file.api.dto.FileUploadRequestDto;
import dev.minguinho.zeze.domain.file.api.dto.FileUrlResponsesDto;
import dev.minguinho.zeze.domain.file.model.S3Uploader;

@Service
@RequiredArgsConstructor
public class FileService {
    private final S3Uploader s3Uploader;

    public FileUrlResponsesDto upload(List<MultipartFile> multipartFiles) {
        List<String> urls = multipartFiles.stream()
            .map(s3Uploader::uploadMultiPartFile)
            .collect(Collectors.toList());
        return new FileUrlResponsesDto(urls);
    }

    public FileUrlResponsesDto upload(FileUploadRequestDto fileUploadRequestDto) throws IOException {
        String url = s3Uploader.uploadExternalFile(fileUploadRequestDto.getFileUrl(),
            fileUploadRequestDto.getFileName());
        return new FileUrlResponsesDto(Collections.singletonList(url));
    }
}
