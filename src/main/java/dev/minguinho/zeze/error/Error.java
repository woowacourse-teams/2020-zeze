package dev.minguinho.zeze.error;

import lombok.Getter;

@Getter
public enum Error {
    GLOBAL_EXCEPTION(0, "시스템 에러"),
    FILE_NOT_CONVERTED(1001, "파일 변환 실패");

    private final Integer code;
    private final String message;

    Error(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
