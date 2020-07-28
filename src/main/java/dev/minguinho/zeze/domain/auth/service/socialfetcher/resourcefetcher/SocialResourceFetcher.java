package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher;

import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponse;
import reactor.core.publisher.Mono;

public interface SocialResourceFetcher {
    Mono<SocialResourceResponse> fetch(SocialResourceRequestDto socialResourceRequestDto);
}
