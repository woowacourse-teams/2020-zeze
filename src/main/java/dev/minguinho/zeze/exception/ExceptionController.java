package dev.minguinho.zeze.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import dev.minguinho.zeze.domain.file.exception.FileNotConvertedException;
import dev.minguinho.zeze.domain.slide.exception.SlideNotAuthorizedException;
import dev.minguinho.zeze.domain.slide.exception.SlideNotFoundException;
import dev.minguinho.zeze.exception.dto.ApiError;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception exception) {
        log.error("message", exception);

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
        return createErrorResponse(fileNotConvertedException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SlideNotFoundException.class)
    public ResponseEntity<ApiError> handleSlideNotFoundException(
        SlideNotFoundException slideNotFoundException
    ) {
        return createErrorResponse(slideNotFoundException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SlideNotAuthorizedException.class)
    public ResponseEntity<ApiError> handleSlideNotAuthorizedException(
        SlideNotAuthorizedException slideNotAuthorizedException
    ) {
        return createErrorResponse(slideNotAuthorizedException, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiError> createErrorResponse(
        RuntimeException runtimeException,
        HttpStatus httpStatus
    ) {
        log.error(runtimeException.getMessage());
        ApiError apiError = ApiError.builder()
            .httpStatus(httpStatus)
            .message(runtimeException.getMessage())
            .build();
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
