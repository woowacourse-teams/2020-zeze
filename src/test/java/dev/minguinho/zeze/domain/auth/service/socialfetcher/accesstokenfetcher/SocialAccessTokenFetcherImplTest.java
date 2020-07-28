package dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.GithubAccessTokenResponseDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.SocialAccessTokenResponse;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class SocialAccessTokenFetcherImplTest {
    private SocialAccessTokenFetcher socialAccessTokenFetcher;
    @Mock
    private SocialAccessTokenFetcher githubAccessTokenFetcher;
    @Mock
    private SocialAccessTokenRequestDto accessTokenRequestDto;

    @BeforeEach
    void setUp() {
        socialAccessTokenFetcher = new SocialAccessTokenFetcherImpl(githubAccessTokenFetcher);
    }

    @DisplayName("access token 가져오기")
    @Test
    void fetchAccessToken_ValidInput_ValidOutput() {
        GithubAccessTokenResponseDto accessTokenResponseDto = GithubAccessTokenResponseDto.builder()
            .accessToken("accessToken")
            .build();
        given(githubAccessTokenFetcher.fetch(accessTokenRequestDto)).willReturn(Mono.just(accessTokenResponseDto));
        given(accessTokenRequestDto.getProvider()).willReturn(Social.Provider.GITHUB);

        SocialAccessTokenResponse response = socialAccessTokenFetcher.fetch(accessTokenRequestDto).block();
        assertThat(response.getAccessToken()).isEqualTo("accessToken");
    }

    @DisplayName("Provider가 잘못 지정되었을 때 예외 발생")
    @Test
    void fetchAccessToken_InvalidInput_Exception() {
        given(accessTokenRequestDto.getProvider()).willReturn(Social.Provider.NONE);
        assertThatThrownBy(() -> socialAccessTokenFetcher.fetch(accessTokenRequestDto))
            .isInstanceOf(IllegalStateException.class);
    }
}
