package dev.minguinho.zeze.file.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.file.exception.FileNotConvertedException;

@Component
@RequiredArgsConstructor
public class S3Uploader {
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.directory}")
    private String directory;
    @Value("#{systemProperties['java.io.tmpdir']}")
    private String tmpDir;

    public String uploadMultiPartFile(MultipartFile multipartFile) {
        File file = convert(multipartFile);
        return upload(file);
    }

    public String uploadExternalFile(String url, String fileName) throws IOException {
        File file = download(url, fileName);
        return upload(file);
    }

    private File convert(MultipartFile multipartFile) {
        File convertedFile = new File(tmpDir + multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException ie) {
            throw new FileNotConvertedException(multipartFile.getOriginalFilename());
        }
        return convertedFile;
    }

    private File download(String url, String fileName) throws IOException {
        InputStream inputStream = new URL(url).openConnection().getInputStream();
        File file = new File(tmpDir + fileName);
        OutputStream outputStream = new FileOutputStream(file);
        int read;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        return file;
    }

    private String upload(File file) {
        String path = String.format("%s/%s-%s", directory, createFilePath(), file.getName());
        amazonS3.putObject(
            new PutObjectRequest(bucket, path, file)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        file.deleteOnExit();
        return amazonS3.getUrl(bucket, path).toString();
    }

    private String createFilePath() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
