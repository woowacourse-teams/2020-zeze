package dev.minguinho.zeze.domain.auth.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
    @DisplayName("User 생성 테스트")
    @Test
    void createUser_validInput_validOutput() {
        Social social = Social.builder()
            .provider(Social.Provider.GITHUB)
            .socialId("socialId")
            .build();

        User user = User.builder()
            .social(social)
            .build();

        assertThat(user.getSocial()).isEqualTo(social);
    }
}
