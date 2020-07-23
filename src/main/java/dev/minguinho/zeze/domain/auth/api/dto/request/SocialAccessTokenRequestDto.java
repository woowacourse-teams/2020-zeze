package dev.minguinho.zeze.domain.auth.api.dto.request;

import dev.minguinho.zeze.domain.auth.model.Social;

public interface SocialAccessTokenRequestDto {
    Social.Provider getProvider();

    String getCode();
}
