package dev.minguinho.zeze.domain.auth.api.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialResourceResponseDto {
    private String socialId;
    private String email;

    @Builder
    private SocialResourceResponseDto(String socialId, String email) {
        this.socialId = socialId;
        this.email = email;
    }
}
