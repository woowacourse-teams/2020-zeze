package dev.minguinho.zeze.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.auth.config.Secured;
import dev.minguinho.zeze.auth.model.Authority;
import dev.minguinho.zeze.user.config.LoginUserId;
import dev.minguinho.zeze.user.dto.UserResourceRequestDto;
import dev.minguinho.zeze.user.dto.UserResourceResponseDto;
import dev.minguinho.zeze.user.service.UserResourceService;

@RequiredArgsConstructor
@RestController
public class LoginUserController {
    private final UserResourceService userResourceService;

    @GetMapping("/api/me")
    @Secured(authority = Authority.Role.ROLE_USER)
    public ResponseEntity<UserResourceResponseDto> getLoginUser(@LoginUserId Long id) {
        UserResourceResponseDto response = userResourceService.retrieveUserResourceBy(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/me")
    @Secured(authority = Authority.Role.ROLE_USER)
    public ResponseEntity<UserResourceResponseDto> updateLoginUser(
        @LoginUserId Long id,
        @Validated @RequestBody UserResourceRequestDto userResourceRequestDto
    ) {
        UserResourceResponseDto response = userResourceService.updateUserResource(id, userResourceRequestDto);
        return ResponseEntity.ok(response);
    }
}
