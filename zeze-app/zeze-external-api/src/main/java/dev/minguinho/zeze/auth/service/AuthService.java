package dev.minguinho.zeze.auth.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.auth.api.dto.response.SocialAccessTokenResponse;
import dev.minguinho.zeze.auth.model.User;
import dev.minguinho.zeze.auth.service.socialfetcher.accesstokenfetcher.SocialAccessTokenFetcher;
import dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.SocialResourceFetcher;
import dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final SocialAccessTokenFetcher accessTokenFetcher;
    private final SocialResourceFetcher resourceFetcher;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticationDto signIn(SocialAccessTokenRequestDto githubAccessTokenRequest) {
        SocialResourceRequestDto socialResourceRequestDto = accessTokenFetcher.fetch(githubAccessTokenRequest)
            .map(SocialAccessTokenResponse::createResourceRequest)
            .block();
        User user = resourceFetcher.fetch(socialResourceRequestDto)
            .map(response -> userService.findOrElseSave(response, githubAccessTokenRequest.getProvider()))
            .block();
        return tokenService.getTokenOf(user);
    }
}
