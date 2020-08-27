package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import dev.minguinho.zeze.domain.auth.model.Social;

public interface SocialResourceRequestDto {
    @NotNull
    Social.Provider getProvider();

    @NotBlank
    String getProviderAccessToken();
}
