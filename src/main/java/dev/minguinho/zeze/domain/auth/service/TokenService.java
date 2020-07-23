package dev.minguinho.zeze.domain.auth.service;

import org.springframework.stereotype.Service;

import dev.minguinho.zeze.domain.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.domain.auth.model.User;

@Service
public class TokenService {
    public AuthenticationDto getTokenOf(User user) {
        return AuthenticationDto.builder()
            .accessToken("abc")
            .build();
    }
}
