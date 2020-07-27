package dev.minguinho.zeze.domain.file.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.minguinho.zeze.domain.file.api.dto.FileUrlResponses;
import dev.minguinho.zeze.domain.file.model.S3Uploader;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
    private final S3Uploader s3Uploader;

    public FileUrlResponses upload(List<MultipartFile> multipartFiles) {
        List<String> urls = multipartFiles.stream()
            .map(s3Uploader::upload)
            .collect(Collectors.toList());
        return new FileUrlResponses(urls);
    }
}
