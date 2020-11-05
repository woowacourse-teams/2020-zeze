package dev.minguinho.zeze.auth.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.auth.infra.JwtTokenProvider;
import dev.minguinho.zeze.auth.model.User;

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
