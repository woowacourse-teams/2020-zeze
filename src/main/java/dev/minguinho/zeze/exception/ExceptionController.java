package dev.minguinho.zeze.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import dev.minguinho.zeze.domain.file.exception.FileNotConvertedException;
import dev.minguinho.zeze.exception.dto.ApiError;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception exception) {
        Arrays.stream(exception.getStackTrace())
            .forEach(content -> log.error(String.valueOf(content)));

        ApiError apiError = ApiError.builder()
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .message("서버 에러")
            .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotConvertedException.class)
    public ResponseEntity<ApiError> handleFileNotConvertedException(
        FileNotConvertedException fileNotConvertedException
    ) {
        log.error(fileNotConvertedException.getMessage());
        ApiError apiError = ApiError.builder()
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message(fileNotConvertedException.getMessage())
            .build();
        return ResponseEntity.badRequest().body(apiError);
    }
}
