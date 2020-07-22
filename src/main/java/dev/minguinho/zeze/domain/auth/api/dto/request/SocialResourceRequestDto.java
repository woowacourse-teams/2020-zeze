package dev.minguinho.zeze.domain.auth.api.dto.request;

import dev.minguinho.zeze.domain.auth.model.Social;

public interface SocialResourceRequestDto {
    Social.Provider getProvider();

    String getProviderAccessToken();
}
