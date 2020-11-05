package dev.minguinho.zeze.exception;


import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import dev.minguinho.zeze.exception.dto.ApiError;

public class ApiErrorConvertor {
    public static ApiError toApiError(HttpStatus httpStatus, String message) {
       return ApiError.builder()
           .status(httpStatus.value())
           .error(httpStatus.getReasonPhrase())
           .message(message)
           .timestamp(ZonedDateTime.now())
           .build();
    }
}
