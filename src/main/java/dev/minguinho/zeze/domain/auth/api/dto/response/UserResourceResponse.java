package dev.minguinho.zeze.domain.auth.api.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResourceResponse {
    private final String name;
    private final String email;
    private final String image;
}
