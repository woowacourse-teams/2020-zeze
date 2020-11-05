package dev.minguinho.zeze.auth.service.socialfetcher.accesstokenfetcher;

import reactor.core.publisher.Mono;

import dev.minguinho.zeze.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.auth.api.dto.response.SocialAccessTokenResponse;

public interface SocialAccessTokenFetcher {
    Mono<SocialAccessTokenResponse> fetch(SocialAccessTokenRequestDto socialAccessTokenRequestDto);
}
