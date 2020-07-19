package dev.minguinho.zeze.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.minguinho.zeze.aws.S3Uploader;
import dev.minguinho.zeze.controller.dto.FileUrlResponses;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PresentationService {
    private final S3Uploader s3Uploader;

    public FileUrlResponses upload(List<MultipartFile> multipartFiles) {
        List<String> urls = multipartFiles.stream()
            .map(s3Uploader::upload)
            .collect(Collectors.toList());
        return new FileUrlResponses(urls);
    }
}
