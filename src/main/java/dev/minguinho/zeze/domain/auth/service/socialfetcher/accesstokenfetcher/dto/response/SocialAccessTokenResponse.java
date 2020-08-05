package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response;

import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;

public interface SocialAccessTokenResponse {
    String getAccessToken();

    SocialResourceRequestDto createResourceRequest();
}
