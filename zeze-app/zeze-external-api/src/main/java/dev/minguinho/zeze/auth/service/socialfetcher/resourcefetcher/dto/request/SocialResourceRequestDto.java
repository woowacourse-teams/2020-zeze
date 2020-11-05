package dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.dto.request;

import dev.minguinho.zeze.auth.model.Social;

public interface SocialResourceRequestDto {
    Social.Provider getProvider();

    String getProviderAccessToken();
}
