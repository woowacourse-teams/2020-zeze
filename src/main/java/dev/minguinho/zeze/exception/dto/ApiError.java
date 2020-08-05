package dev.minguinho.zeze.exception.dto;

import static java.time.ZonedDateTime.*;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiError {
    private ZonedDateTime timestamp;
    private Integer status;
    private String error;
    private String message;

    @Builder
    public ApiError(HttpStatus httpStatus, String message) {
        this.timestamp = now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
    }
}
