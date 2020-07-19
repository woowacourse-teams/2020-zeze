package dev.minguinho.zeze;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.minguinho.zeze.common.DefaultResponseEntity;
import dev.minguinho.zeze.error.Error;
import dev.minguinho.zeze.exception.FileNotConvertedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(FileNotConvertedException.class)
    public ResponseEntity<DefaultResponseEntity<Void>> handleFileNotConvertedException(
        FileNotConvertedException fileNotConvertedException) {
        log.error(fileNotConvertedException.getMessage());
        return ResponseEntity.badRequest().body(DefaultResponseEntity.error(Error.FILE_NOT_CONVERTED));
    }
}
