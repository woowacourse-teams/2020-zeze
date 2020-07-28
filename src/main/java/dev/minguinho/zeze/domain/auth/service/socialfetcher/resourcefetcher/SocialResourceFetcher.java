package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher;

import reactor.core.publisher.Mono;

import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponse;

public interface SocialResourceFetcher {
    Mono<SocialResourceResponse> fetch(SocialResourceRequestDto socialResourceRequestDto);
}
