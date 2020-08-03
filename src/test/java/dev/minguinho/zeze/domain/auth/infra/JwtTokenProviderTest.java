package dev.minguinho.zeze.domain.auth.infra;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.minguinho.zeze.domain.auth.model.Authority;

class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider("secret", 100000);
    }

    @Test
    void jwtTokenCreate_ValidInput_ValidOutput() {
        Set<Authority> authorities = Collections.singleton(Authority.USER);
        String token = jwtTokenProvider.createToken(1L, authorities);
        assertThat(token).isNotBlank();
    }

    @Test
    void jwtTokenParse_ValidInput_ValidOutput() {
        String token = jwtTokenProvider.createToken(1L, Collections.singleton(Authority.ADMIN));
        Long userId = jwtTokenProvider.getUserId(token);
        Set<Authority.Role> authorities = jwtTokenProvider.getAuthorities(token);
        assertThat(userId).isEqualTo(1L);
        assertThat(authorities).contains(Authority.Role.ROLE_ADMIN);
    }

    @Test
    void jwtTokenValidation_ValidInput_ValidOutput() {
        Set<Authority> authorities = Collections.singleton(Authority.ADMIN);
        String token = jwtTokenProvider.createToken(1L, authorities);
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }
}
