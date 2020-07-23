package dev.minguinho.zeze.domain.auth.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResourceResponse {
    private final String name;
    private final String email;

    @Builder
    private UserResourceResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
