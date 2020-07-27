package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    private static final String ACCESS_TOKEN_FIELD_NAME = "access_token";

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
            .map(this::createAccessToken);
    }

    private SocialAccessTokenResponseDto createAccessToken(JsonNode body) {
        String accessToken = Optional.ofNullable(body.get(ACCESS_TOKEN_FIELD_NAME))
            .map(JsonNode::asText)
            .filter(token -> !StringUtils.isEmpty(token))
            .orElseThrow(() -> new IllegalArgumentException(
                "ResponseBody does not have the field: " + ACCESS_TOKEN_FIELD_NAME
            ));

        return SocialAccessTokenResponseDto.builder()
            .accessToken(accessToken)
            .build();
    }
}
