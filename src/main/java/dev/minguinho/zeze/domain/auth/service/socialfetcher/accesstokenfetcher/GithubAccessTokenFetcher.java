package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.request.GithubAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.GithubAccessTokenResponseDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.SocialAccessTokenResponse;

@Service
public class GithubAccessTokenFetcher implements SocialAccessTokenFetcher {
    private static final String BASE_URL = "https://github.com/";

    private final WebClient webClient;
    private final String clientId;
    private final String clientSecret;

    public GithubAccessTokenFetcher(
        WebClient.Builder webClientBuilder,
        @Value("${github.client.id}") String clientId,
        @Value("${github.client.secret}") String clientSecret
    ) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public Mono<SocialAccessTokenResponse> fetch(SocialAccessTokenRequestDto socialAccessTokenRequestDto) {
        if (socialAccessTokenRequestDto.getProvider() != Social.Provider.GITHUB) {
            throw new IllegalStateException(
                "Only GITHUB is supported. Input : " + socialAccessTokenRequestDto.getProvider().toString()
            );
        }

        GithubAccessTokenRequestDto githubAccessTokenRequestDto = GithubAccessTokenRequestDto.builder()
            .code(socialAccessTokenRequestDto.getCode())
            .clientId(clientId)
            .clientSecret(clientSecret)
            .build();

        return webClient.post()
            .uri("/login/oauth/access_token")
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(githubAccessTokenRequestDto)
            .retrieve()
            .bodyToMono(GithubAccessTokenResponseDto.class)
            .cast(SocialAccessTokenResponse.class);
    }
}
