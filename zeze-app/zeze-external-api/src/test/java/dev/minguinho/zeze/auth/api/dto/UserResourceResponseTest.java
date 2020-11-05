package dev.minguinho.zeze.auth.api.dto;

import dev.minguinho.zeze.user.dto.UserResourceResponseDto;

class UserResourceResponseTest {
    public static UserResourceResponseDto getUserResourceResponseFixture() {
        UserResourceResponseDto userResourceResponseDto = UserResourceResponseDto.builder()
            .name("foo")
            .email("foo@bar.com")
            .build();

        return userResourceResponseDto;
    }
}
