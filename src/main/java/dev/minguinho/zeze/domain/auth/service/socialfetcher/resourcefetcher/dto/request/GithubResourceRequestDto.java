package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import dev.minguinho.zeze.domain.auth.model.Social;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GithubResourceRequestDto implements SocialResourceRequestDto {
    @NotNull
    private final Social.Provider provider = Social.Provider.GITHUB;
    @NotBlank
    private final String providerAccessToken;

    @Builder
    private GithubResourceRequestDto(String providerAccessToken) {
        this.providerAccessToken = providerAccessToken;
    }
}
