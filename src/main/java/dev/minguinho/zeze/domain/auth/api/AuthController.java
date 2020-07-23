package dev.minguinho.zeze.domain.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.minguinho.zeze.domain.auth.api.dto.request.GithubSignInDto;
import dev.minguinho.zeze.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/signin/github")
    public ResponseEntity<Void> signIn(@RequestBody GithubSignInDto signInDto) {
        authService.signIn(signInDto);
        return ResponseEntity.ok().build();
    }
}
