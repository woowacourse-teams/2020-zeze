package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialAccessTokenResponseDto {
    private final @NotBlank String accessToken;

    @Builder
    private SocialAccessTokenResponseDto(@NotBlank String accessToken) {
        this.accessToken = accessToken;
    }
}
