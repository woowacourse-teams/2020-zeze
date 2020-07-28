package dev.minguinho.zeze.domain.user.api.dto;

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
