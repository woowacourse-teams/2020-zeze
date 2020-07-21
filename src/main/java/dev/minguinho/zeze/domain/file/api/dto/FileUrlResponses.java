package dev.minguinho.zeze.domain.file.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FileUrlResponses {
    private final List<String> urls;
}
