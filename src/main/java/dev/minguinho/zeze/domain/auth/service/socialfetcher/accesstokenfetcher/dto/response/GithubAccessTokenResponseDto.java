package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response;

import javax.validation.constraints.NotBlank;

import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.GithubResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialAccessTokenResponseDto implements SocialAccessTokenResponse {
    private final @NotBlank String accessToken;

    @Builder
    private SocialAccessTokenResponseDto(@NotBlank String accessToken) {
        this.accessToken = accessToken;
    }

    public SocialResourceRequestDto createResourceRequest(Social.Provider provider) {
        switch (provider) {
            case GITHUB:
                return GithubResourceRequestDto.builder()
                    .providerAccessToken(accessToken)
                    .build();
            default:
                throw new IllegalStateException("잘못된 형식의 Social입니다. Input: " + provider);
        }
    }

    @Override
    public SocialResourceRequestDto createResourceRequest() {
        return GithubResourceRequestDto.builder()
            .providerAccessToken(accessToken)
            .build();
    }
}
