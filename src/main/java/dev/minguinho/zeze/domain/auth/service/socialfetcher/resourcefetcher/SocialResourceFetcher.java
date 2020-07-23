package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher;

import java.util.Optional;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponseDto;
import reactor.core.publisher.Mono;

public interface SocialResourceFetcher {
    static SocialResourceResponseDto createResource(JsonNode body, Social.Provider provider) {
        String socialId = Optional.ofNullable(body.get(provider.getIdFieldName()))
            .map(JsonNode::asText)
            .filter(id -> !StringUtils.isEmpty(id))
            .orElseThrow(() -> new IllegalArgumentException(
                "ResponseBody does not have the field: " + provider.getIdFieldName()
            ));
        String email = Optional.ofNullable(body.get(provider.getEmailFieldName()))
            .map(JsonNode::asText)
            .orElse("");
        String name = Optional.ofNullable(body.get(provider.getNameFieldName()))
            .map(JsonNode::asText)
            .orElse("");
        String image = Optional.ofNullable(body.get(provider.getImageFieldName()))
            .map(JsonNode::asText)
            .orElse("");

        return SocialResourceResponseDto.builder()
            .socialId(socialId)
            .email(email)
            .name(name)
            .image(image)
            .build();
    }

    Mono<SocialResourceResponseDto> fetch(SocialResourceRequestDto socialResourceRequestDto);
}
