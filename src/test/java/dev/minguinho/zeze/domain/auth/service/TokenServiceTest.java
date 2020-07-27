package dev.minguinho.zeze.domain.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.minguinho.zeze.domain.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.domain.auth.model.User;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    private TokenService tokenService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService(jwtTokenProvider);
    }

    @DisplayName("토큰 생성 테스트")
    @Test
    void createToken_ValidInput_ValidOutput() {
        given(jwtTokenProvider.createToken(anyString())).willReturn("token");
        given(user.getId()).willReturn(1L);

        AuthenticationDto authenticationDto = tokenService.getTokenOf(user);

        assertThat(authenticationDto.getAccessToken()).isEqualTo("token");
    }
}
