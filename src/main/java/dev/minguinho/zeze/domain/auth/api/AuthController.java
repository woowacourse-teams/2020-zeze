package dev.minguinho.zeze.domain.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import dev.minguinho.zeze.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    public ResponseEntity<Void> signIn() {
        return ResponseEntity.ok().build();
    }
}
