package dev.minguinho.zeze.domain.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.auth.api.dto.request.GithubSignInDto;
import dev.minguinho.zeze.domain.auth.api.dto.response.AuthenticationDto;
import dev.minguinho.zeze.domain.auth.service.AuthService;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/signin/github")
    public ResponseEntity<AuthenticationDto> signIn(@RequestBody GithubSignInDto signInDto) {
        AuthenticationDto authenticationDto = authService.signIn(signInDto);
        return ResponseEntity.ok(authenticationDto);
    }
}
