package dev.minguinho.zeze.domain.auth.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.auth.model.UserRepository;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponse;
import dev.minguinho.zeze.domain.user.model.UserResourceRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserResourceRepository userResourceRepository;

    public User findOrElseSave(SocialResourceResponse response, Social.Provider provider) {
        Social social = Social.builder()
            .provider(provider)
            .socialId(response.getSocialId())
            .build();
        return userRepository.findBySocial(social)
            .orElseGet(() -> {
                User user = userRepository.save(response.createUser(provider));
                userResourceRepository.save(response.createUserResource(user.getId()));
                return user;
            });
    }
}
