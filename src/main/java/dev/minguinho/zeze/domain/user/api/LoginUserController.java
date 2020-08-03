package dev.minguinho.zeze.domain.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import dev.minguinho.zeze.domain.user.api.dto.UserResourceRequestDto;
import dev.minguinho.zeze.domain.user.api.dto.UserResourceResponseDto;
import dev.minguinho.zeze.domain.user.config.LoginedUserId;
import dev.minguinho.zeze.domain.user.service.UserResourceService;

@RequiredArgsConstructor
@RestController
public class LoginUserController {
    private final UserResourceService userResourceService;

    @GetMapping("me")
    public ResponseEntity<UserResourceResponseDto> getLoginUser(@LoginedUserId Long id) {
        UserResourceResponseDto response = userResourceService.retrieveUserResourceBy(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("me")
    public ResponseEntity<UserResourceResponseDto> updateLoginUser(@LoginedUserId Long id,
        UserResourceRequestDto userResourceRequestDto) {
        UserResourceResponseDto response = userResourceService.updateUserResource(id, userResourceRequestDto);
        return ResponseEntity.ok(response);
    }
}
