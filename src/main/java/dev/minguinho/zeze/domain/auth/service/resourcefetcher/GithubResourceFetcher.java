package dev.minguinho.zeze.domain.auth.service.resourcefetcher;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import dev.minguinho.zeze.domain.auth.api.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.api.dto.request.SocialResourceResponseDto;
import dev.minguinho.zeze.domain.auth.model.Social;
import reactor.core.publisher.Mono;

@Service
public class GithubResourceFetcher implements SocialResourceFetcher {
    private static final String BASE_URL = "https://api.github.com";
    private final WebClient webClient;

    public GithubResourceFetcher(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    public Mono<SocialResourceResponseDto> fetchUserResource(SocialResourceRequestDto socialResourceRequestDto) {
        if (socialResourceRequestDto.getProvider() != Social.Provider.GITHUB) {
            throw new IllegalStateException(
                "Only GITHUB is supported. Input : " + socialResourceRequestDto.getProvider().toString()
            );
        }

        return webClient.get()
            .header("Authorization", "token " + socialResourceRequestDto.getProviderAccessToken())
            .retrieve()
            .bodyToMono(JsonNode.class)
            .map(body -> SocialResourceFetcher.createResource(body, "id"));
    }
}
