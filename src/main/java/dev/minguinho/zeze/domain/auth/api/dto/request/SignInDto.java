package dev.minguinho.zeze.domain.auth.api.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import dev.minguinho.zeze.domain.auth.model.Social;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInDto implements SocialResourceRequestDto {
    private @NotNull Social.Provider provider;
    private @NotEmpty String providerAccessToken;
}
