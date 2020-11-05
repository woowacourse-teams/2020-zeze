package dev.minguinho.zeze.auth.service;

import dev.minguinho.zeze.auth.model.Social;
import dev.minguinho.zeze.auth.model.User;
import dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponse;
import dev.minguinho.zeze.user.model.UserResource;

public class UserConvertor {
    public static User toUser(SocialResourceResponse socialResourceResponse, Social.Provider provider) {
        return User.builder()
            .social(
                Social.builder()
                    .provider(provider)
                    .socialId(socialResourceResponse.getSocialId())
                    .build()
            ).build();
    }

    public static UserResource toUserResource(Long userId, SocialResourceResponse socialResourceResponse) {
        return UserResource.builder()
            .userId(userId)
            .email(socialResourceResponse.getEmail())
            .name(socialResourceResponse.getName())
            .profileImage(socialResourceResponse.getProfileImage())
            .build();
    }
}
