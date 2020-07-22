package dev.minguinho.zeze.domain.auth.service.resourcefetcher;

import java.util.Optional;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import dev.minguinho.zeze.domain.auth.api.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.api.dto.request.SocialResourceResponseDto;
import reactor.core.publisher.Mono;

public interface SocialResourceFetcher {
    static SocialResourceResponseDto createResource(JsonNode body, String idFieldName) {
        String socialId = Optional.ofNullable(body.get(idFieldName))
            .map(JsonNode::asText)
            .filter(id -> !StringUtils.isEmpty(id))
            .orElseThrow(() -> new IllegalArgumentException(
                "ResponseBody does not have the field: " + idFieldName
            ));

        String email = Optional.ofNullable(body.get("email"))
            .map(JsonNode::asText)
            .orElse("");
        return SocialResourceResponseDto.builder()
            .socialId(socialId)
            .email(email)
            .build();
    }

    Mono<SocialResourceResponseDto> fetchUserResource(SocialResourceRequestDto socialResourceRequestDto);
}
