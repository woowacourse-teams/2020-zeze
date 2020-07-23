package dev.minguinho.zeze.domain.auth.api.dto.request;

import javax.validation.constraints.NotNull;

import dev.minguinho.zeze.domain.auth.model.Social;
import lombok.Getter;

@Getter
public class GithubSignInDto implements SocialAccessTokenRequestDto {
    private @NotNull Social.Provider provider;
    private @NotNull String code;
}
