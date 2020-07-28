package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GithubAccessTokenRequestDto {
    @JsonProperty("code")
    private String code;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;

    @Builder
    public GithubAccessTokenRequestDto(String code, String clientId, String clientSecret) {
        this.code = code;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
