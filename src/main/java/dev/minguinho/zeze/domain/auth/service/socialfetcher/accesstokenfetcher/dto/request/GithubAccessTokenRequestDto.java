package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GithubAccessTokenRequestDto {
    private String code;
    private String clientId;
    private String clientSecret;

    @Builder
    public GithubAccessTokenRequestDto(String code, String clientId, String clientSecret) {
        this.code = code;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
