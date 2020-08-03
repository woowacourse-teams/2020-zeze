package dev.minguinho.zeze.domain.auth.api.dto.response;

import dev.minguinho.zeze.domain.user.api.dto.UserResourceResponseDto;

class UserResourceResponseTest {
    public static UserResourceResponseDto getUserResourceResponseFixture() {
        UserResourceResponseDto userResourceResponseDto = UserResourceResponseDto.builder()
            .name("foo")
            .email("foo@bar.com")
            .build();

        return userResourceResponseDto;
    }
}
