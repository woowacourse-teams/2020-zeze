package dev.minguinho.zeze.domain.auth.service;

import org.springframework.stereotype.Service;

import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.SocialAccessTokenFetcher;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.SocialResourceFetcher;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final SocialAccessTokenFetcher accessTokenFetcher;
    private final SocialResourceFetcher resourceFetcher;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticationDto signIn(SocialAccessTokenRequestDto githubAccessTokenRequest) {
        SocialResourceRequestDto socialResourceRequestDto = accessTokenFetcher.fetch(githubAccessTokenRequest)
            .map(response -> response.createResourceRequest(githubAccessTokenRequest.getProvider()))
            .block();
        User user = resourceFetcher.fetch(socialResourceRequestDto)
            .map(response -> userService.findOrElseSave(response, githubAccessTokenRequest.getProvider()))
            .block();
        return tokenService.getTokenOf(user);
    }
}
