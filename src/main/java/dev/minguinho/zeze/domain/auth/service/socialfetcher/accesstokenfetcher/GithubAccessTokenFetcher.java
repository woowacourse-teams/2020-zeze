package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.request.GithubAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.SocialAccessTokenResponseDto;
import reactor.core.publisher.Mono;

@Service
public class GithubAccessTokenFetcher implements SocialAccessTokenFetcher {
    private static final String BASE_URL = "https://github.com/";
    private final WebClient webClient;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;

    public GithubAccessTokenFetcher(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    @Override
    public Mono<SocialAccessTokenResponseDto> fetch(SocialAccessTokenRequestDto socialAccessTokenRequestDto) {
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
            .bodyToMono(JsonNode.class)
            .map(json -> SocialAccessTokenFetcher.createAccessToken(json, "access_token"));
    }
}
