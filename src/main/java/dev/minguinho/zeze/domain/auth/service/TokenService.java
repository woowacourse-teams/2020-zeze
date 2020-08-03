package dev.minguinho.zeze.domain.auth.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.domain.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.domain.auth.model.User;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationDto getTokenOf(User user) {
        String token = jwtTokenProvider.createToken(user.getId(), user.getAuthorities());
        return AuthenticationDto.builder()
            .accessToken(token)
            .build();
    }
}
