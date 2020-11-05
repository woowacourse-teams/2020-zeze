package dev.minguinho.zeze.auth.service;

import static dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.dto.response.GithubResourceResponseDtoTest.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;

import dev.minguinho.zeze.auth.dto.AuthenticationDto;
import dev.minguinho.zeze.auth.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.auth.dto.response.SocialAccessTokenResponse;
import dev.minguinho.zeze.auth.model.Social;
import dev.minguinho.zeze.auth.model.User;
import dev.minguinho.zeze.auth.service.socialfetcher.accesstokenfetcher.SocialAccessTokenFetcher;
import dev.minguinho.zeze.auth.service.socialfetcher.accesstokenfetcher.dto.response.GithubAccessTokenResponseDto;
import dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.SocialResourceFetcher;
import dev.minguinho.zeze.auth.service.socialfetcher.resourcefetcher.dto.response.GithubResourceResponseDto;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private AuthService authService;
    @Mock
    private SocialAccessTokenFetcher accessTokenFetcher;
    @Mock
    private SocialResourceFetcher resourceFetcher;
    @Mock
    private UserService userService;
    @Mock
    private TokenService tokenService;
    @Mock
    private User user;
    @Mock
    private SocialAccessTokenRequestDto accessTokenRequestDto;

    @BeforeEach
    void setUp() {
        authService = new AuthService(accessTokenFetcher, resourceFetcher, userService, tokenService);
    }

    @DisplayName("AuthService 로그인 테스트")
    @Test
    void signIn_ValidInput_ValidOutput() {
        SocialAccessTokenResponse accessTokenResponseDto = GithubAccessTokenResponseDto.builder()
            .accessToken("accessToken")
            .build();
        given(accessTokenFetcher.fetch(any())).willReturn(Mono.just(accessTokenResponseDto));
        given(accessTokenRequestDto.getProvider()).willReturn(Social.Provider.GITHUB);
        GithubResourceResponseDto githubResourceResponseDto = getGithubResourceFixture();
        given(resourceFetcher.fetch(any())).willReturn(Mono.just(githubResourceResponseDto));
        given(userService.findOrElseSave(any(), any())).willReturn(user);
        AuthenticationDto authenticationDto = AuthenticationDto.builder()
            .accessToken("accessToken")
            .build();
        given(tokenService.getTokenOf(any(User.class))).willReturn(authenticationDto);

        AuthenticationDto response = authService.signIn(accessTokenRequestDto);
        assertThat(response.getAccessToken()).isEqualTo("accessToken");
    }
}
