package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response;

import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.user.model.UserResource;

public interface SocialResourceResponse {
    String getSocialId();

    String getEmail();

    String getName();

    String getProfileImage();

    User createUser(Social.Provider provider);

    UserResource createUserResource(Long userId);
}
