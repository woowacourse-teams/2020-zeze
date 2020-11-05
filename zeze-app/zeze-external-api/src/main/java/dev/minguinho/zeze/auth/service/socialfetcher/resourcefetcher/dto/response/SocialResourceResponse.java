package dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.dto.response;

import dev.minguinho.zeze.auth.model.Social;
import dev.minguinho.zeze.auth.model.User;
import dev.minguinho.zeze.user.model.UserResource;

public interface SocialResourceResponse {
    String getSocialId();

    String getEmail();

    String getName();

    String getProfileImage();

    User createUser(Social.Provider provider);

    UserResource createUserResource(Long userId);
}
