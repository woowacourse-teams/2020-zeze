package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.GithubResourceResponseDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponse;
import reactor.core.publisher.Mono;

@Service
public class GithubResourceFetcher implements SocialResourceFetcher {
    private static final String BASE_URL = "https://api.github.com";
    private final WebClient webClient;

    public GithubResourceFetcher(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    public Mono<SocialResourceResponse> fetch(SocialResourceRequestDto socialResourceRequestDto) {
        if (socialResourceRequestDto.getProvider() != Social.Provider.GITHUB) {
            throw new IllegalStateException(
                "Only GITHUB is supported. Input : " + socialResourceRequestDto.getProvider().toString()
            );
        }

        return webClient.get()
            .uri("/user")
            .header("Authorization", "token " + socialResourceRequestDto.getProviderAccessToken())
            .retrieve()
            .bodyToMono(GithubResourceResponseDto.class)
            .cast(SocialResourceResponse.class);
    }
}
