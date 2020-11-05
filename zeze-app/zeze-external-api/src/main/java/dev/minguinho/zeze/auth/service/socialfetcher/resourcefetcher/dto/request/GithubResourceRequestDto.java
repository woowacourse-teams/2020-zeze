package dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

import dev.minguinho.zeze.auth.model.Social;

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
