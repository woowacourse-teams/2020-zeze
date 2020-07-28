package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.SocialAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Primary
@Service
public class SocialAccessTokenFetcherImpl implements SocialAccessTokenFetcher {
    private final SocialAccessTokenFetcher githubAccessTokenFetcher;

    @Override
    public Mono<SocialAccessTokenResponse> fetch(
        SocialAccessTokenRequestDto socialAccessTokenRequestDto) {
        switch (socialAccessTokenRequestDto.getProvider()) {
            case GITHUB:
                return githubAccessTokenFetcher.fetch(socialAccessTokenRequestDto);
            default:
                throw new IllegalStateException("잘못된 형식의 로그인 방법입니다.");
        }
    }
}
