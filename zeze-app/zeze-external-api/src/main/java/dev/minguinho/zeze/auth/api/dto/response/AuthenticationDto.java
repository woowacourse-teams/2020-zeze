package dev.minguinho.zeze.auth.api.dto.response;

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
