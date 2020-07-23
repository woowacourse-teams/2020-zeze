package dev.minguinho.zeze.domain.auth.api.dto.response;

class UserResourceResponseTest {
    public static UserResourceResponse getUserResourceResponseFixture() {
        UserResourceResponse userResourceResponse = UserResourceResponse.builder()
            .name("foo")
            .email("foo@bar.com")
            .build();

        return userResourceResponse;
    }
}
