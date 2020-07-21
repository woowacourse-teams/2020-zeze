package dev.minguinho.zeze.domain.file.api.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadRequestDto {
    private List<MultipartFile> files;
}
