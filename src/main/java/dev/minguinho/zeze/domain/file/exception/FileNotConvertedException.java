package dev.minguinho.zeze.domain.file.exception;

public class FileNotConvertedException extends RuntimeException {
    public FileNotConvertedException(String fileName) {
        super(fileName + "의 파일 변환에 실패했습니다.");
    }
}
