package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher;

import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.SocialAccessTokenResponseDto;
import reactor.core.publisher.Mono;

public interface SocialAccessTokenFetcher {
    Mono<SocialAccessTokenResponseDto> fetch(SocialAccessTokenRequestDto socialAccessTokenRequestDto);
}
