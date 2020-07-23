package dev.minguinho.zeze.domain.auth.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.auth.model.UserRepository;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.SocialAccessTokenFetcher;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.SocialResourceFetcher;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponseDto;
import dev.minguinho.zeze.domain.resource.model.UserResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final SocialAccessTokenFetcher accessTokenFetcher;
    private final SocialResourceFetcher resourceFetcher;
    private final UserRepository userRepository;
    private final UserResourceRepository userResourceRepository;
    private final TokenService tokenService;

    public AuthenticationDto signIn(SocialAccessTokenRequestDto githubAccessTokenRequest) {
        SocialResourceRequestDto socialResourceRequestDto = accessTokenFetcher.fetch(githubAccessTokenRequest)
            .map(response -> response.createResourceRequest(githubAccessTokenRequest.getProvider()))
            .block();
        SocialResourceResponseDto resourceResponseDto =
            Objects.requireNonNull(resourceFetcher.fetch(socialResourceRequestDto).block());
        User user = userRepository.save(resourceResponseDto.createUser(socialResourceRequestDto.getProvider()));
        userResourceRepository.save(resourceResponseDto.createUserResource(user.getId()));

        return tokenService.getTokenOf(user);
    }
}
