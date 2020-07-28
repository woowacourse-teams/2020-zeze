package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.GithubResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;

@Getter
@NoArgsConstructor
public class GithubAccessTokenResponseDto implements SocialAccessTokenResponse {
    @JsonProperty("access_token")
    private @NotBlank
    String accessToken;

    @Builder
    private GithubAccessTokenResponseDto(@NotBlank String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public SocialResourceRequestDto createResourceRequest() {
        return GithubResourceRequestDto.builder()
            .providerAccessToken(accessToken)
            .build();
    }
}
