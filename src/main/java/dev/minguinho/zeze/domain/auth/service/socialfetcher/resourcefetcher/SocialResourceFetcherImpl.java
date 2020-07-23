package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponseDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Primary
@Service
public class SocialResourceFetcherImpl implements SocialResourceFetcher {
    private final SocialResourceFetcher githubResourceFetcher;

    @Override
    public Mono<SocialResourceResponseDto> fetch(
        SocialResourceRequestDto socialResourceRequestDto) {
        switch (socialResourceRequestDto.getProvider()) {
            case GITHUB:
                return githubResourceFetcher.fetch(socialResourceRequestDto);
            default:
                throw new IllegalStateException("잘못된 형식의 로그인 방법입니다.");
        }
    }
}
