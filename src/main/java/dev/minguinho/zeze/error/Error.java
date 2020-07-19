package dev.minguinho.zeze.error;

import lombok.Getter;

@Getter
public enum Error {
    FILE_NOT_CONVERTED(1001, "파일 변환 실패");

    private final Integer code;
    private final String message;

    Error(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
