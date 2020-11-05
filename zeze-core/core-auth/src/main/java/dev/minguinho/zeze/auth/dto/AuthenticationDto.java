package dev.minguinho.zeze.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationDto {
    private String accessToken;

    @Builder
    private AuthenticationDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
