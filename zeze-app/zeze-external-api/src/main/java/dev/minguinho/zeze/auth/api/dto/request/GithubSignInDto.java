package dev.minguinho.zeze.auth.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

import dev.minguinho.zeze.auth.model.Social;

@Getter
public class GithubSignInDto implements SocialAccessTokenRequestDto {
    @NotNull
    private Social.Provider provider;
    @NotBlank
    private String code;
}
