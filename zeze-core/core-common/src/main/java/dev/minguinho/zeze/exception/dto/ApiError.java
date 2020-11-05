package dev.minguinho.zeze.exception.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiError {
    private Integer status;
    private String error;
    private String message;
    private ZonedDateTime timestamp;

    @Builder
    private ApiError(Integer status, String error, String message, ZonedDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }
}
