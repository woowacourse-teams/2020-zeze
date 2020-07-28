package dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher;

import static dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.GithubResourceResponseDtoTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.minguinho.zeze.domain.auth.model.Social;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.request.SocialResourceRequestDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.GithubResourceResponseDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponse;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class SocialResourceFetcherImplTest {
    private SocialResourceFetcher socialResourceFetcher;
    @Mock
    private SocialResourceFetcher githubResourceFetcher;
    @Mock
    private SocialResourceRequestDto socialResourceRequestDto;

    @BeforeEach
    void setUp() {
        socialResourceFetcher = new SocialResourceFetcherImpl(githubResourceFetcher);
    }

    @DisplayName("유저 정보 가져 오기")
    @Test
    void fetchResource_ValidInput_ValidOutput() {
        GithubResourceResponseDto githubResourceResponseDto = getGithubResourceFixture();
        given(githubResourceFetcher.fetch(socialResourceRequestDto)).willReturn(Mono.just(githubResourceResponseDto));
        given(socialResourceRequestDto.getProvider()).willReturn(Social.Provider.GITHUB);

        SocialResourceResponse response = socialResourceFetcher.fetch(socialResourceRequestDto).block();

        assertAll(
            () -> assertThat(response.getSocialId()).isEqualTo("socialId"),
            () -> assertThat(response.getEmail()).isEqualTo("email"),
            () -> assertThat(response.getName()).isEqualTo("name"),
            () -> assertThat(response.getProfileImage()).isEqualTo("image")
        );
    }

    @DisplayName("잘못된 유저 정보 가져오기 예외")
    @Test
    void fetchResource_InvalidInput_Exception() {
        given(socialResourceRequestDto.getProvider()).willReturn(Social.Provider.NONE);
        assertThatThrownBy(() -> socialResourceFetcher.fetch(socialResourceRequestDto))
            .isInstanceOf(IllegalStateException.class);
    }
}
