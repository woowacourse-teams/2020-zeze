package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response;

import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.resource.model.UserResource;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SocialResourceResponseDto {
    private String socialId;
    private String email;
    private String name;
    private String image;

    public User createUser(Social.Provider provider) {
        return User.builder()
            .social(
                Social.builder()
                    .provider(provider)
                    .socialId(socialId)
                    .build()
            ).build();
    }

    public UserResource createUserResource(Long userId) {
        return UserResource.builder()
            .userId(userId)
            .email(email)
            .name(name)
            .image(image)
            .build();
    }
}


