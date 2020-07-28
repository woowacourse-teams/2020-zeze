package dev.minguinho.zeze.domain.file.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.file.exception.FileNotConvertedException;

@Component
@RequiredArgsConstructor
public class S3Uploader {
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.directory}")
    private String directory;

    public String upload(MultipartFile multipartFile) {
        File file = convert(multipartFile);
        String path = String.format("%s/%s-%s", directory, createFilePath(), file.getName());
        amazonS3.putObject(
            new PutObjectRequest(bucket, path, file)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        file.deleteOnExit();
        return amazonS3.getUrl(bucket, path).toString();
    }

    private File convert(MultipartFile multipartFile) {
        File convertedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException ie) {
            throw new FileNotConvertedException(multipartFile.getOriginalFilename());
        }
        return convertedFile;
    }

    private String createFilePath() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
