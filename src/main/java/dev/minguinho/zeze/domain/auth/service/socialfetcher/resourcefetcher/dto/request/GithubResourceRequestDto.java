package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request;

import dev.minguinho.zeze.domain.auth.model.Social;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GithubResourceRequestDto implements SocialResourceRequestDto {
    private final Social.Provider provider = Social.Provider.GITHUB;
    private final String providerAccessToken;

    @Builder
    private GithubResourceRequestDto(String providerAccessToken) {
        this.providerAccessToken = providerAccessToken;
    }
}
