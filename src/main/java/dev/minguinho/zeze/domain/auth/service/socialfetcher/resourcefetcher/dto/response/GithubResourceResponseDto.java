package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.user.model.UserResource;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class GithubResourceResponseDto implements SocialResourceResponse {
    @JsonProperty("id")
    private String socialId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
    @JsonProperty("avatar_url")
    private String profileImage;

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
            .profileImage(profileImage)
            .build();
    }
}
