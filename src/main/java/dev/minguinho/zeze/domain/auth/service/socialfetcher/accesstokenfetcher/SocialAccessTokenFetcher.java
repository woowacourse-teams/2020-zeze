package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher;

import java.util.Optional;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.SocialAccessTokenResponseDto;
import reactor.core.publisher.Mono;

public interface SocialAccessTokenFetcher {
    static SocialAccessTokenResponseDto createAccessToken(JsonNode body, String accessTokenFieldName) {
        String accessToken = Optional.ofNullable(body.get(accessTokenFieldName))
            .map(JsonNode::asText)
            .filter(token -> !StringUtils.isEmpty(token))
            .orElseThrow(() -> new IllegalArgumentException(
                "ResponseBody does not have the field: " + accessTokenFieldName
            ));

        return SocialAccessTokenResponseDto.builder()
            .accessToken(accessToken)
            .build();
    }

    Mono<SocialAccessTokenResponseDto> fetch(SocialAccessTokenRequestDto socialAccessTokenRequestDto);
}
