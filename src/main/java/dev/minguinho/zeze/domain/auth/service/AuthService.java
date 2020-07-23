package dev.minguinho.zeze.domain.auth.service;

import org.springframework.stereotype.Service;

import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.api.dto.response.UserResourceResponse;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.auth.model.UserRepository;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.SocialAccessTokenFetcher;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.SocialAccessTokenResponseDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.SocialResourceFetcher;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.GithubResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponseDto;
import dev.minguinho.zeze.domain.resource.model.UserResource;
import dev.minguinho.zeze.domain.resource.model.UserResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final SocialAccessTokenFetcher accessTokenFetcher;
    private final SocialResourceFetcher resourceFetcher;
    private final UserRepository userRepository;
    private final UserResourceRepository userResourceRepository;

    public UserResourceResponse signIn(SocialAccessTokenRequestDto githubAccessTokenRequest) {
        SocialAccessTokenResponseDto accessToken = accessTokenFetcher.fetch(githubAccessTokenRequest).block();
        SocialResourceRequestDto socialResourceRequestDto = GithubResourceRequestDto.builder()
            .providerAccessToken(accessToken.getAccessToken())
            .build();
        SocialResourceResponseDto resourceResponseDto = resourceFetcher.fetch(socialResourceRequestDto).block();
        User user = userRepository.save(resourceResponseDto.createUser(socialResourceRequestDto.getProvider()));
        UserResource userResource = userResourceRepository.save(resourceResponseDto.createUserResource(user.getId()));

        return UserResourceResponse.builder()
            .name(userResource.getName())
            .email(userResource.getEmail())
            .build();
    }
}
