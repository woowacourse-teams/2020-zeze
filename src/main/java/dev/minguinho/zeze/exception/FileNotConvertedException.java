package dev.minguinho.zeze.exception;

public class FileNotConvertedException extends RuntimeException {
    public FileNotConvertedException(String fileName) {
        super(fileName + "의 파일변환에 실패했습니다.");
    }
}
