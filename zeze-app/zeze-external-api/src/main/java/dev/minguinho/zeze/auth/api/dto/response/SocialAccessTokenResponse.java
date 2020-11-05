package dev.minguinho.zeze.auth.api.dto.response;

import dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;

public interface SocialAccessTokenResponse {
    String getAccessToken();

    SocialResourceRequestDto createResourceRequest();
}
