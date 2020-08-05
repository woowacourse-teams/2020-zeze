package dev.minguinho.zeze.domain.user.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import dev.minguinho.zeze.domain.user.model.UserResource;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResourceResponseDto {
    private final String name;
    private final String email;
    private final String profileImage;

    public static UserResourceResponseDto from(UserResource userResource) {
        return UserResourceResponseDto.builder()
            .name(userResource.getName())
            .email(userResource.getEmail())
            .profileImage(userResource.getProfileImage())
            .build();
    }
}
