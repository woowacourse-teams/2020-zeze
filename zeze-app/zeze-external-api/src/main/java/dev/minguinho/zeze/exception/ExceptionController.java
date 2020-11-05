package dev.minguinho.zeze.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import dev.minguinho.zeze.auth.exception.InvalidTokenException;
import dev.minguinho.zeze.auth.exception.NotAuthorizedException;
import dev.minguinho.zeze.exception.dto.ApiError;
import dev.minguinho.zeze.file.exception.FileNotConvertedException;
import dev.minguinho.zeze.slide.exception.SlideNotAuthorizedException;
import dev.minguinho.zeze.slide.exception.SlideNotFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception exception) {
        log.error("message", exception);
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError apiError = ApiErrorConvertor.toApiError(internalServerError, "서버에러");
        return new ResponseEntity<>(apiError, internalServerError);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleUnAuthenticatedException(InvalidTokenException invalidTokenException) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiError apiError = ApiErrorConvertor.toApiError(unauthorized, invalidTokenException.getMessage());
        return ResponseEntity.status(unauthorized).body(apiError);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ApiError> handleNotAuthorizedException(NotAuthorizedException notAuthorizedException) {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        ApiError apiError = ApiErrorConvertor.toApiError(forbidden, notAuthorizedException.getMessage());
        return ResponseEntity.status(forbidden).body(apiError);
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
        ApiError apiError = ApiErrorConvertor.toApiError(httpStatus, runtimeException.getMessage());
        return ResponseEntity.status(httpStatus).body(apiError);
    }
}
