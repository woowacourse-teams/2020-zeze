package dev.minguinho.zeze.domain.file.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FileUploadRequestDto {
    private String fileUrl;
    private String fileName;
}
