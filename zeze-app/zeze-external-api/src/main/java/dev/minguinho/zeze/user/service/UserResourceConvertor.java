package dev.minguinho.zeze.user.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import dev.minguinho.zeze.user.dto.UserResourceRequestDto;
import dev.minguinho.zeze.user.dto.UserResourceResponseDto;
import dev.minguinho.zeze.user.model.UserResource;

public class UserResourceConvertor {
    public static UserResource toEntity(UserResourceRequestDto userResourceRequestDto) {
        return UserResource.builder()
            .name(userResourceRequestDto.getName())
            .email(userResourceRequestDto.getEmail())
            .profileImage(userResourceRequestDto.getProfileImage())
            .build();
    }

    public static UserResourceResponseDto toUserResourceResponseDto(UserResource userResource) {
        return UserResourceResponseDto.builder()
            .name(userResource.getName())
            .email(userResource.getEmail())
            .profileImage(userResource.getProfileImage())
            .build();
    }
}
