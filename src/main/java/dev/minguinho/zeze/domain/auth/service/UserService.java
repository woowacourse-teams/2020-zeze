package dev.minguinho.zeze.domain.auth.service;

import org.springframework.stereotype.Service;

import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.auth.model.UserRepository;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponseDto;
import dev.minguinho.zeze.domain.resource.model.UserResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserResourceRepository userResourceRepository;

    public User save(SocialResourceResponseDto response, Social.Provider provider) {
        Social social = Social.builder()
            .provider(provider)
            .socialId(response.getSocialId())
            .build();
        User user = userRepository.findBySocial(social)
            .orElse(userRepository.save(response.createUser(provider)));
        userResourceRepository.save(response.createUserResource(user.getId()));
        return user;
    }
}
