package dev.minguinho.zeze.domain.file.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FileUrlResponsesDto {
    private final List<String> urls;
}
