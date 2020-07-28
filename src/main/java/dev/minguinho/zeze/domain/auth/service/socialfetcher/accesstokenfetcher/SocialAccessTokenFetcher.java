package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher;

import reactor.core.publisher.Mono;

import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.SocialAccessTokenResponse;

public interface SocialAccessTokenFetcher {
    Mono<SocialAccessTokenResponse> fetch(SocialAccessTokenRequestDto socialAccessTokenRequestDto);
}
