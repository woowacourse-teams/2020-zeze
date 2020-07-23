package dev.minguinho.zeze.domain.auth.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import dev.minguinho.zeze.domain.auth.model.Social;

public interface SocialAccessTokenRequestDto {
    @NotNull Social.Provider getProvider();

    @NotBlank String getCode();
}
