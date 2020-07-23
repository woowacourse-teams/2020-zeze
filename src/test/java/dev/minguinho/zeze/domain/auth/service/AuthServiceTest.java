package dev.minguinho.zeze.domain.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.minguinho.zeze.domain.auth.api.dto.request.SocialAccessTokenRequestDto;
import dev.minguinho.zeze.domain.auth.api.dto.response.UserResourceResponse;
import dev.minguinho.zeze.domain.auth.model.User;
import dev.minguinho.zeze.domain.auth.model.UserRepository;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.SocialAccessTokenFetcher;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.accesstokenfetcher.dto.response.SocialAccessTokenResponseDto;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.SocialResourceFetcher;
import dev.minguinho.zeze.domain.auth.service.socialfetcher.resourcefetcher.dto.response.SocialResourceResponseDto;
import dev.minguinho.zeze.domain.resource.model.UserResource;
import dev.minguinho.zeze.domain.resource.model.UserResourceRepository;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private AuthService authService;
    @Mock
    private SocialAccessTokenFetcher accessTokenFetcher;
    @Mock
    private SocialResourceFetcher resourceFetcher;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserResourceRepository userResourceRepository;
    @Mock
    private User user;
    @Mock
    private UserResource userResource;
    @Mock
    private SocialAccessTokenRequestDto accessTokenRequestDto;

    @BeforeEach
    void setUp() {
        authService = new AuthService(accessTokenFetcher, resourceFetcher, userRepository, userResourceRepository);
    }

    @DisplayName("AuthService 로그인 테스트")
    @Test
    void signIn_ValidInput_ValidOutput() {
        SocialAccessTokenResponseDto accessTokenResponseDto = SocialAccessTokenResponseDto.builder()
            .accessToken("accessToken")
            .build();
        given(accessTokenFetcher.fetch(any())).willReturn(Mono.just(accessTokenResponseDto));
        SocialResourceResponseDto socialResourceResponseDto = SocialResourceResponseDto.builder()
            .socialId("soialId")
            .email("email")
            .name("name")
            .build();
        given(resourceFetcher.fetch(any())).willReturn(Mono.just(socialResourceResponseDto));
        given(userRepository.save(any())).willReturn(user);
        given(user.getId()).willReturn(1L);
        given(userResourceRepository.save(any())).willReturn(userResource);
        given(userResource.getName()).willReturn("name");
        given(userResource.getEmail()).willReturn("email");

        UserResourceResponse userResourceResponse = authService.signIn(accessTokenRequestDto);
        assertAll(
            () -> assertThat(userResourceResponse.getEmail()).isEqualTo("email"),
            () -> assertThat(userResourceResponse.getName()).isEqualTo("name")
        );
    }
}
